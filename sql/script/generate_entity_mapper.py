import re
import os

def sql_type_to_java_type(sql_type):
    sql_type = sql_type.lower()
    if 'int' in sql_type:
        return 'Integer'
    elif 'varchar' in sql_type or 'text' in sql_type:
        return 'String'
    elif 'datetime' in sql_type:
        return 'LocalDateTime'
    elif 'decimal' in sql_type:
        return 'BigDecimal'
    elif 'bit' in sql_type:
        return 'Boolean'
    else:
        return 'String'

def camel_case(s):
    return ''.join(word.capitalize() for word in s.split('_'))

def generate_entity_and_mapper(sql_file, base_package, output_dir):
    with open(sql_file, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # 提取所有表结构
    tables = re.findall(r'DROP TABLE IF EXISTS `(\w+)`;.*?CREATE TABLE `(\1)`.*?\((.*?)\);', content, re.DOTALL | re.IGNORECASE)
    
    for drop_table, table_name, columns_part in tables:
        entity_name = camel_case(table_name)
        # 解析列定义
        columns = re.findall(r'`(\w+)`\s+(\w+.*?)(?:,|$)', columns_part, re.DOTALL)
        
        # 生成实体类代码
        entity_code = f'package {base_package}.entity;\n\n'
        entity_code += 'import java.time.LocalDateTime;\n'
        entity_code += 'import java.math.BigDecimal;\n'
        entity_code += 'import lombok.Data;\n\n'
        entity_code += f'@Data\n'
        entity_code += f'public class {entity_name} {{\n'
        
        for col_name, col_type in columns:
            if col_name == 'id':
                continue  # 主键在后面单独处理
            java_type = sql_type_to_java_type(col_type)
            field_name = camel_case(col_name)
            entity_code += f'    private {java_type} {field_name};\n'
        
        # 添加主键
        entity_code += '\n    private Integer id;\n'
        entity_code += '}\n'
        
        # 生成mapper接口代码
        mapper_code = f'package {base_package}.mapper;\n\n'
        mapper_code += f'import {base_package}.entity.{entity_name};\n'
        mapper_code += 'import org.apache.ibatis.annotations.Mapper;\n\n'
        mapper_code += f'@Mapper\n'
        mapper_code += f'public interface {entity_name}Mapper {{\n'
        mapper_code += f'    {entity_name} selectByPrimaryKey(Integer id);\n'
        mapper_code += f'    int insert({entity_name} record);\n'
        mapper_code += f'    int updateByPrimaryKey({entity_name} record);\n'
        mapper_code += '}\n'
        
        # 创建目录并写入文件
        entity_dir = os.path.join(output_dir, 'src/main/java', base_package.replace('.', '/'), 'entity')
        os.makedirs(entity_dir, exist_ok=True)
        entity_file = os.path.join(entity_dir, f'{entity_name}.java')
        with open(entity_file, 'w', encoding='utf-8') as f:
            f.write(entity_code)
        
        mapper_dir = os.path.join(output_dir, 'src/main/java', base_package.replace('.', '/'), 'mapper')
        os.makedirs(mapper_dir, exist_ok=True)
        mapper_file = os.path.join(mapper_dir, f'{entity_name}Mapper.java')
        with open(mapper_file, 'w', encoding='utf-8') as f:
            f.write(mapper_code)

if __name__ == '__main__':
    generate_entity_and_mapper(
        sql_file='/Users/shadowalker/Documents/workSpace/cses-exam/sql/xzs-mysql-ddl-init.sql',
        base_package='com.cses.exam',
        output_dir='/Users/shadowalker/Documents/workSpace/cses-exam/'
    )