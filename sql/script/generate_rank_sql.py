import re

def generate_rank_sql(config_path, output_path):
    # 读取配置文件内容
    try:
        with open(config_path, 'r', encoding='utf-8') as f:
            lines = [line.strip() for line in f.readlines() if line.strip()]
        print(f"成功读取配置文件: {config_path}, 共 {len(lines)} 行数据")
    except FileNotFoundError:
        print(f"错误: 配置文件 {config_path} 不存在")
        return
    except Exception as e:
        print(f"读取配置文件时出错: {str(e)}")
        return

    # 生成SQL语句
    sql_statements = []
    # 添加文件头注释
    sql_statements.append("-- 自动生成的排名数据")
    sql_statements.append("-- 来源配置文件: xzs-mysql-rank-config.sql")
    sql_statements.append("-- 生成规则: 严格按配置文件顺序，不做任何优化\n")

    # 处理每一行数据
    for line_num, line in enumerate(lines, 1):
        # 跳过注释行
        if line.startswith('--'):
            continue

        # 匹配排名和组合格式 (例如: 1    (5,5,5,5,5))
        match = re.match(r'^(\d+)\s+\(([^)]+)\)$', line)
        if not match:
            print(f"警告: 第 {line_num} 行格式不正确，已跳过 - 内容: {line}")
            continue

        rank = match.group(1)
        combo = match.group(2)

        # 计算总分
        try:
            scores = list(map(int, combo.split(',')))
            total_score = sum(scores)
            combo_hash = ''.join(str(score) for score in scores)
        except Exception as e:
            print(f"警告: 第 {line_num} 行分数解析失败，已跳过 - 错误: {str(e)}")
            continue

        # 生成SQL语句
        sql = f"INSERT INTO `t_rank_config` (`rank`, `score_combination`, `combination_hash`, `total_score`) VALUES ({rank}, '{combo}', '{combo_hash}', {total_score});"
        sql_statements.append(sql)

    # 写入输出文件
    try:
        with open(output_path, 'w', encoding='utf-8') as f:
            f.write('\n'.join(sql_statements))
        print(f"成功生成 {len(sql_statements)-3} 条SQL语句，保存至: {output_path}")
    except Exception as e:
        print(f"写入输出文件时出错: {str(e)}")
        return

def main():
    # 配置文件路径
    config_sql_path = '/Users/shadowalker/Documents/workSpace/cses-exam/sql/config/xzs-mysql-rank-config.sql'
    # 输出文件路径
    output_sql_path = '/Users/shadowalker/Documents/workSpace/cses-exam/sql/xzs-mysql-dml-init-rank.sql'

    # 生成SQL
    generate_rank_sql(config_sql_path, output_sql_path)

if __name__ == '__main__':
    main()