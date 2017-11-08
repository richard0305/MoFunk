package ylj.mofunk.model.tools;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/16
 *     desc  : �ַ�����ع�����
 * </pre>
 */
public class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * �ж��ַ����Ƿ�Ϊnull�򳤶�Ϊ0
     *
     * @param s ��У���ַ���
     * @return {@code true}: ��<br> {@code false}: ��Ϊ��
     */
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * �ж��ַ����Ƿ�Ϊnull��ȫΪ�ո�
     *
     * @param s ��У���ַ���
     * @return {@code true}: null��ȫ�ո�<br> {@code false}: ��Ϊnull�Ҳ�ȫ�ո�
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * �ж����ַ����Ƿ����
     *
     * @param a ��У���ַ���a
     * @param b ��У���ַ���b
     * @return {@code true}: ���<br>{@code false}: �����
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * �ж����ַ������Դ�Сд�Ƿ����
     *
     * @param a ��У���ַ���a
     * @param b ��У���ַ���b
     * @return {@code true}: ���<br>{@code false}: �����
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        return (a == b) || (b != null) && (a.length() == b.length()) && a.regionMatches(true, 0, b, 0, b.length());
    }

    /**
     * nullתΪ����Ϊ0���ַ���
     *
     * @param s ��ת�ַ���
     * @return sΪnullתΪ����Ϊ0�ַ��������򲻸ı�
     */
    public static String null2Length0(String s) {
        return s == null ? "" : s;
    }

    /**
     * �����ַ�������
     *
     * @param s �ַ���
     * @return null����0����������������
     */
    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * ����ĸ��д
     *
     * @param s ��ת�ַ���
     * @return ����ĸ��д�ַ���
     */
    public static String upperFirstLetter(String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * ����ĸСд
     *
     * @param s ��ת�ַ���
     * @return ����ĸСд�ַ���
     */
    public static String lowerFirstLetter(String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * ��ת�ַ���
     *
     * @param s ����ת�ַ���
     * @return ��ת�ַ���
     */
    public static String reverse(String s) {
        int len = length(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * ת��Ϊ����ַ�
     *
     * @param s ��ת�ַ���
     * @return ����ַ���
     */
    public static String toDBC(String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * ת��Ϊȫ���ַ�
     *
     * @param s ��ת�ַ���
     * @return ȫ���ַ���
     */
    public static String toSBC(String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }
}
