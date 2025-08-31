#!/usr/bin/env python3
import csv

# 配置文件路径
INPUT_FILE = '/Users/shadowalker/Documents/workSpace/cses-exam/sql/config/question-option-config.sql'
OUTPUT_FILE = '/Users/shadowalker/Documents/workSpace/cses-exam/sql/xzs-mysql-dml-init-question-options.sql'

# 存储解析后的选项数据: {question_id: [(content, level, score), ...]}
options_data = {}

with open(INPUT_FILE, 'r', encoding='utf-8') as f:
    # 使用制表符分隔读取文件
    reader = csv.reader(f, delimiter='\t')
    
    for row_num, row in enumerate(reader, start=1):
        # 跳过空行
        if not row:
            continue
        
        # 验证字段数量 (假设格式: question_id\tcontent\tlevel\tscore)
        if len(row) != 4:
            print(f"警告: 第{row_num}行格式不正确，字段数={len(row)}，已跳过")
            continue
        
        question_id_str, content, level_str, score_str = row
        
        # 转换数据类型
        try:
            question_id = int(question_id_str)
            level = int(level_str)
            score = int(score_str)
        except ValueError as e:
            print(f"警告: 第{row_num}行数据转换失败: {str(e)}，已跳过")
            continue
        
        # 转义SQL中的单引号
        escaped_content = content.replace("'", "''")
        
        # 按question_id分组存储
        if question_id not in options_data:
            options_data[question_id] = []
        options_data[question_id].append((escaped_content, level, score))

# 生成SQL文件
with open(OUTPUT_FILE, 'w', encoding='utf-8') as f_out:
    # 写入SQL头部
    f_out.write('INSERT INTO `t_question_option` (`question_id`, `content`, `level`, `score`)\nVALUES\n')
    
    # 收集所有选项的VALUES子句
    values_clauses = []
    for qid in sorted(options_data.keys()):
        # 按level排序选项
        sorted_options = sorted(options_data[qid], key=lambda x: x[1])
        for content, level, score in sorted_options:
            values_clauses.append(f"({qid}, '{content}', {level}, {score})")
    
    # 用逗号连接所有VALUES子句并写入
    f_out.write(',\n'.join(values_clauses) + ';\n')

print(f"成功生成SQL文件: {OUTPUT_FILE}")