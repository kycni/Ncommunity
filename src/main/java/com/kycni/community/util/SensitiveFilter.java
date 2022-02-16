package com.kycni.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kycni
 * @date 2022/2/16 12:08
 */
@Component
public class SensitiveFilter {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);
    
    private static final String REPLACE = "***";
    // 声明根节点（前缀树根节点不存字符）
    private TrieNode rootNode = new TrieNode();
    
    // 初始化前缀树
    @PostConstruct
    public void init () {
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.text");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                ) {
            String keyword;            
            while ((keyword = reader.readLine()) != null) {
                // 添加到前缀树
                this.addKeyword(keyword);
            }
        } catch (IOException e) {
            logger.error("获取敏感词文件出错：" + e.getMessage());
        }

    }
    
    // 添加关键字到前缀树
    private void addKeyword (String keyword) {
        TrieNode tempNode = rootNode;
        
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            // * 获取子节点
            TrieNode subNode = tempNode.getSubNode(c);
            // 子节点为空情况下，创建子节点
            if (subNode == null) {
                // 初始化节点
                subNode = new TrieNode();
                tempNode.addSubNodes(c, subNode);
            }
            // 有值的话，继续遍历
            tempNode = subNode;
            
            // 终止条件，遍历到最后一个字符时停止，设置终止条件
            if (i == keyword.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     * 过滤敏感信息的方法
     * @return 过滤后的信息
     */
    public String filter (String text) {
        // 前端传入参数，考虑进行判空
        if (StringUtils.isBlank(text)) {
            return null;
        }
        // 指针1
        TrieNode temptNode = rootNode;
        // 指针2
        int begin = 0;
        // 指针3
        int position = 0;
        // 返回结果
        StringBuilder sb = new StringBuilder();
        // 拆分传入的字符串
        while (position < text.length()) {
            char c = text.charAt(position);

            // 包含特殊字符的情况
            // 跳过符号
            if (isSymbal(c)) {
                if (temptNode == rootNode) {
                    // 计入结果
                    sb.append(c);
                    begin++;
                }
                position++;
                continue;
            }
        
            // 每次循环都检查它的下级节点
            temptNode = temptNode.getSubNode(c);
            // 不是敏感词
            if (temptNode == null) {
                // 存入StringBuilder
                sb.append(text.charAt(begin));
                // begin先走，position跟上
                position = ++begin;
                // 重新指向根节点
                temptNode = rootNode;
            } else if (temptNode.isKeywordEnd()) {
                // 有敏感词，将begin到position的字符替换
                sb.append(REPLACE);
                // 进入下一个位置
                begin = ++position;
                // 重新指向根节点
                temptNode = rootNode;
            } else {
                position++;
            }
        }
        sb.append(text.substring(begin));
        return sb.toString();
    }
    
    // 是特殊字符，并不包含东亚字符
    private boolean isSymbal(char c) {
       return  !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    // 定义一个前缀树的类对象
    private class TrieNode {
        // 声明终止条件，初始值为false
        private boolean isKeywordEnd = false;
        
        // 声明子节点
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        // 添加子节点
        public void addSubNodes (Character c, TrieNode subNode) {
            subNodes.put(c, subNode);
        }
        
        // 获取子节点
        public TrieNode getSubNode (Character c) {
            return subNodes.get(c);
        }
        
        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }
    }
}
