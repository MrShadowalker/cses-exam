#!/usr/bin/env python3
import csv

# 定义映射关系
target_type_map = {'成人': 'adult', '青少年': 'teenager'}
scene_map = {'生活': 'life', '健康': 'health', '工作': 'work', '社交': 'social', '学习': 'study'}

# 输入和输出文件路径
input_file = '/Users/shadowalker/Documents/workSpace/cses-exam/sql/config/question-config.sql'
output_file = '/Users/shadowalker/Documents/workSpace/cses-exam/sql/xzs-mysql-dml-init-question.sql'

with open(input_file, 'r', encoding='utf-8') as f_in, open(output_file, 'w', encoding='utf-8') as f_out:
    # 跳过表头行
    next(f_in)
    
    for line_num, line in enumerate(f_in, start=2):  # 从第2行开始计数
        line = line.strip()
        if not line:
            continue
        
        # 使用制表符分割字段
        fields = line.split('\t')
        if len(fields) != 5:
            print(f"警告: 第{line_num}行格式不正确，跳过该行: {line}")
            continue
        
        category, subject, scene_type, weight, content = fields
        
        # 转换分类
        target_type = target_type_map.get(category)
        if not target_type:
            print(f"警告: 第{line_num}行未知分类 '{category}'，跳过该行")
            continue
        
        # 转换场景类型
        scene = scene_map.get(scene_type)
        if not scene:
            print(f"警告: 第{line_num}行未知场景类型 '{scene_type}'，跳过该行")
            continue
        
        # 处理subject为整数
        try:
            subject_int = int(subject)
        except ValueError:
            print(f"警告: 第{line_num}行环节 '{subject}' 不是有效整数，跳过该行")
            continue
        
        # 处理weight为浮点数
        try:
            weight_float = float(weight)
        except ValueError:
            print(f"警告: 第{line_num}行权重 '{weight}' 不是有效数字，跳过该行")
            continue
        
        # 转义内容中的单引号
        escaped_content = content.replace("'", "''")
        
        # 生成INSERT语句
        sql = f"INSERT INTO `t_question` (`target_type`, `subject`, `scene`, `weight`, `content`) VALUES ('{target_type}', {subject_int}, '{scene}', {weight_float}, '{escaped_content}');\n"
        f_out.write(sql)

print(f"成功生成SQL文件: {output_file}")