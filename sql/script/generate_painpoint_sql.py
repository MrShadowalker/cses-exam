import os

def generate_painpoint_sql(config_path, output_path):
    # 读取配置文件
    with open(config_path, 'r', encoding='utf-8') as f:
        lines = f.readlines()

    # 跳过表头行，处理数据行
    values = []
    for line_num, line in enumerate(lines[1:], start=2):  # 从第二行开始处理
        line = line.strip()
        if not line:
            continue

        # 使用制表符分割字段（处理5个字段：环节、环节名称、层级、痛点名称、痛点描述）
        parts = line.split('\t')
        if len(parts) != 5:
            print(f"警告：第{line_num}行格式不正确（字段数={len(parts)}），跳过处理")
            continue

        subject, subject_name, level, painpoint_name, painpoint_desc = parts

        # 处理单引号转义
        painpoint_name = painpoint_name.replace("'", "''")
        painpoint_desc = painpoint_desc.replace("'", "''")

        # 构建VALUES子句（新增name和description字段映射）
        values.append(f"({subject}, '{subject_name}', {level}, '{painpoint_name}', '{painpoint_desc}')")

    # 构建完整SQL语句
    sql = f"""INSERT INTO `t_painpoint_config` (`subject`, `subject_name`, `level`, `name`, `description`)
VALUES
{',\n'.join(values)};"""

    # 写入输出文件
    with open(output_path, 'w', encoding='utf-8') as f:
        f.write(sql)

    print(f"成功生成{len(values)}条记录，保存至{output_path}")

if __name__ == "__main__":
    # 配置文件路径（当前使用用户提供的最新配置文件）
    config_file = "/Users/shadowalker/Documents/workSpace/cses-exam/sql/config/xzs-mysql-painpoint-config.sql"
    # 输出文件路径
    output_file = "/Users/shadowalker/Documents/workSpace/cses-exam/sql/xzs-mysql-dml-init-painpoint.sql"
    generate_painpoint_sql(config_file, output_file)