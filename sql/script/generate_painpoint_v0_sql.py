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

        # 使用制表符分割字段
        parts = line.split('\t')
        if len(parts) != 4:
            print(f"警告：第{line_num}行格式不正确，跳过处理")
            continue

        subject, subject_name, level, painpoint = parts

        # 处理单引号转义
        painpoint = painpoint.replace("'", "''")

        # 构建VALUES子句
        values.append(f"({subject}, '{subject_name}', {level}, '{painpoint}')")

    # 构建完整SQL语句
    sql = f"""INSERT INTO `t_painpoint_config_v0` (`subject`, `subject_name`, `level`, `painpoint`)
VALUES
{',\n'.join(values)};"""

    # 写入输出文件
    with open(output_path, 'w', encoding='utf-8') as f:
        f.write(sql)

    print(f"成功生成{len(values)}条记录，保存至{output_path}")

if __name__ == "__main__":
    # 配置文件路径
    config_file = "/Users/shadowalker/Documents/workSpace/cses-exam/sql/config/xzs-mysql-painpoint-config-v0.sql"
    # 输出文件路径
    output_file = "/Users/shadowalker/Documents/workSpace/cses-exam/sql/xzs-mysql-dml-init-painpoint-v0.sql"
    generate_painpoint_sql(config_file, output_file)