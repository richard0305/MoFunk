package ylj.mofunk.model.tools;



import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ylj.mofunk.model.tools.ConstUtils.REGEX_DATE;
import static ylj.mofunk.model.tools.ConstUtils.REGEX_EMAIL;
import static ylj.mofunk.model.tools.ConstUtils.REGEX_ID_CARD15;
import static ylj.mofunk.model.tools.ConstUtils.REGEX_ID_CARD18;
import static ylj.mofunk.model.tools.ConstUtils.REGEX_IP;
import static ylj.mofunk.model.tools.ConstUtils.REGEX_MOBILE_EXACT;
import static ylj.mofunk.model.tools.ConstUtils.REGEX_MOBILE_SIMPLE;
import static ylj.mofunk.model.tools.ConstUtils.REGEX_TEL;
import static ylj.mofunk.model.tools.ConstUtils.REGEX_URL;
import static ylj.mofunk.model.tools.ConstUtils.REGEX_USERNAME;
import static ylj.mofunk.model.tools.ConstUtils.REGEX_ZH;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : ������ع�����
 * </pre>
 */
public class RegexUtils {

    private RegexUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * If u want more please visit http://toutiao.com/i6231678548520731137/
     */

    /**
     * ��֤�ֻ��ţ��򵥣�
     *
     * @param input ����֤�ı�
     * @return {@code true}: ƥ��<br>{@code false}: ��ƥ��
     */
    public static boolean isMobileSimple(CharSequence input) {
        return isMatch(REGEX_MOBILE_SIMPLE, input);
    }

    /**
     * ��֤�ֻ��ţ���ȷ��
     *
     * @param input ����֤�ı�
     * @return {@code true}: ƥ��<br>{@code false}: ��ƥ��
     */
    public static boolean isMobileExact(CharSequence input) {
        return isMatch(REGEX_MOBILE_EXACT, input);
    }

    /**
     * ��֤�绰����
     *
     * @param input ����֤�ı�
     * @return {@code true}: ƥ��<br>{@code false}: ��ƥ��
     */
    public static boolean isTel(CharSequence input) {
        return isMatch(REGEX_TEL, input);
    }

    /**
     * ��֤���֤����15λ
     *
     * @param input ����֤�ı�
     * @return {@code true}: ƥ��<br>{@code false}: ��ƥ��
     */
    public static boolean isIDCard15(CharSequence input) {
        return isMatch(REGEX_ID_CARD15, input);
    }

    /**
     * ��֤���֤����18λ
     *
     * @param input ����֤�ı�
     * @return {@code true}: ƥ��<br>{@code false}: ��ƥ��
     */
    public static boolean isIDCard18(CharSequence input) {
        return isMatch(REGEX_ID_CARD18, input);
    }

    /**
     * ��֤����
     *
     * @param input ����֤�ı�
     * @return {@code true}: ƥ��<br>{@code false}: ��ƥ��
     */
    public static boolean isEmail(CharSequence input) {
        return isMatch(REGEX_EMAIL, input);
    }

    /**
     * ��֤URL
     *
     * @param input ����֤�ı�
     * @return {@code true}: ƥ��<br>{@code false}: ��ƥ��
     */
    public static boolean isURL(CharSequence input) {
        return isMatch(REGEX_URL, input);
    }

    /**
     * ��֤����
     *
     * @param input ����֤�ı�
     * @return {@code true}: ƥ��<br>{@code false}: ��ƥ��
     */
    public static boolean isZh(CharSequence input) {
        return isMatch(REGEX_ZH, input);
    }

    /**
     * ��֤�û���
     * <p>ȡֵ��ΧΪa-z,A-Z,0-9,"_",���֣�������"_"��β,�û���������6-20λ</p>
     *
     * @param input ����֤�ı�
     * @return {@code true}: ƥ��<br>{@code false}: ��ƥ��
     */
    public static boolean isUsername(CharSequence input) {
        return isMatch(REGEX_USERNAME, input);
    }

    /**
     * ��֤yyyy-MM-dd��ʽ������У�飬�ѿ���ƽ����
     *
     * @param input ����֤�ı�
     * @return {@code true}: ƥ��<br>{@code false}: ��ƥ��
     */
    public static boolean isDate(CharSequence input) {
        return isMatch(REGEX_DATE, input);
    }

    /**
     * ��֤IP��ַ
     *
     * @param input ����֤�ı�
     * @return {@code true}: ƥ��<br>{@code false}: ��ƥ��
     */
    public static boolean isIP(CharSequence input) {
        return isMatch(REGEX_IP, input);
    }

    /**
     * �ж��Ƿ�ƥ������
     *
     * @param regex ������ʽ
     * @param input Ҫƥ����ַ���
     * @return {@code true}: ƥ��<br>{@code false}: ��ƥ��
     */
    public static boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    /**
     * ��ȡ����ƥ��Ĳ���
     *
     * @param regex ������ʽ
     * @param input Ҫƥ����ַ���
     * @return ����ƥ��Ĳ���
     */
    public static List<String> getMatches(String regex, CharSequence input) {
        if (input == null) return null;
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * ��ȡ����ƥ�����
     *
     * @param input Ҫ������ַ���
     * @param regex ������ʽ
     * @return ����ƥ�����
     */
    public static String[] getSplits(String input, String regex) {
        if (input == null) return null;
        return input.split(regex);
    }

    /**
     * �滻����ƥ��ĵ�һ����
     *
     * @param input       Ҫ�滻���ַ���
     * @param regex       ������ʽ
     * @param replacement ������
     * @return �滻����ƥ��ĵ�һ����
     */
    public static String getReplaceFirst(String input, String regex, String replacement) {
        if (input == null) return null;
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }

    /**
     * �滻��������ƥ��Ĳ���
     *
     * @param input       Ҫ�滻���ַ���
     * @param regex       ������ʽ
     * @param replacement ������
     * @return �滻��������ƥ��Ĳ���
     */
    public static String getReplaceAll(String input, String regex, String replacement) {
        if (input == null) return null;
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }
}