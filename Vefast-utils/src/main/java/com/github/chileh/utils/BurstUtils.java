package com.github.chileh.utils;

/**
 * 分片桶字段算法工具类，用于生成分片或分桶的标识字符串。
 */
public class BurstUtils {

    public final static String SPLIT_CHAR = "-";

    /**
     * 将多个字段拼接成一个字符串，字段之间用指定的分隔符分隔。
     * @param fields 要拼接的字段数组。可以传入任意数量的对象，每个对象将被转换为字符串。
     * @return 拼接后的字符串。如果输入字段为空，则返回空字符串。
     */
    public static String encrypt(Object... fields){
        StringBuffer sb  = new StringBuffer();
        if(fields!=null&&fields.length>0) {
            sb.append(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                sb.append(SPLIT_CHAR).append(fields[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 默认第一组，生成一个以"0"开头的分片桶字段字符串。
     *<p>
     * 该方法会将传入的字段拼接成一个字符串，字段之间用指定的分隔符（默认为"-"）分隔，并在字符串的最前面添加一个固定的分组标识"0"。
     *
     * @param fields 要拼接的字段数组。可以传入任意数量的对象，每个对象将被转换为字符串。
     * @return 拼接后的字符串。如果输入字段为空，则返回"0"。
     */
    public static String groupOne(Object... fields){
        StringBuffer sb  = new StringBuffer();
        if(fields!=null&&fields.length>0) {
            sb.append("0");
            for (Object field : fields) {
                sb.append(SPLIT_CHAR).append(field);
            }
        }
        return sb.toString();
    }
}

