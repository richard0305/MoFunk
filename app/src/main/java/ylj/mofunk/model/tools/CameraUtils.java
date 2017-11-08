package ylj.mofunk.model.tools;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/19
 *     desc  : �����ع�����
 * </pre>
 */
public class CameraUtils {

//    private CameraUtils() {
//        throw new UnsupportedOperationException("u can't instantiate me...");
//    }
//
//    /**
//     * ��ȡ���ճ�������Intent
//     */
//    public static Intent getOpenCameraIntent() {
//        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//    }
//
//    /**
//     * ��ȡ��ת�����ѡ������Intent
//     */
//    public static Intent getImagePickerIntent() {
//        Intent intent = new Intent(Intent.ACTION_PICK, null);
//        return intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//    }
//
//    /**
//     * ��ȡ[��ת�����ѡ�����,����ת���ü����棬Ĭ�Ͽ����Ųü�����]��Intent
//     */
//    public static Intent getImagePickerIntent(int outputX, int outputY, Uri fromFileURI,
//                                              Uri saveFileURI) {
//        return getImagePickerIntent(1, 1, outputX, outputY, true, fromFileURI, saveFileURI);
//    }
//
//    /**
//     * ��ȡ[��ת�����ѡ�����,����ת���ü����棬Ĭ�Ͽ����Ųü�����]��Intent
//     */
//    public static Intent getImagePickerIntent(int aspectX, int aspectY, int outputX, int outputY, Uri fromFileURI,
//                                              Uri saveFileURI) {
//        return getImagePickerIntent(aspectX, aspectY, outputX, outputY, true, fromFileURI, saveFileURI);
//    }
//
//    /**
//     * ��ȡ[��ת�����ѡ�����,����ת���ü����棬����ָ���Ƿ����Ųü�����]��Intent
//     *
//     * @param aspectX     �ü���ߴ����X
//     * @param aspectY     �ü���ߴ����Y
//     * @param outputX     ����ߴ���
//     * @param outputY     ����ߴ�߶�
//     * @param canScale    �Ƿ������
//     * @param fromFileURI �ļ���Դ·��URI
//     * @param saveFileURI ����ļ�·��URI
//     */
//    public static Intent getImagePickerIntent(int aspectX, int aspectY, int outputX, int outputY, boolean canScale,
//                                              Uri fromFileURI, Uri saveFileURI) {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setDataAndType(fromFileURI, "image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", aspectX <= 0 ? 1 : aspectX);
//        intent.putExtra("aspectY", aspectY <= 0 ? 1 : aspectY);
//        intent.putExtra("outputX", outputX);
//        intent.putExtra("outputY", outputY);
//        intent.putExtra("scale", canScale);
//        // ͼƬ���ò���ڱ߽��
//        intent.putExtra("scaleUpIfNeeded", true);
//        intent.putExtra("return-data", false);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, saveFileURI);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        // ȥ������ʶ��
//        return intent.putExtra("noFaceDetection", true);
//    }
//
//    /**
//     * ��ȡ[��ת�����ѡ�����,����ת���ü����棬Ĭ�Ͽ����Ųü�����]��Intent
//     */
//    public static Intent getCameraIntent(Uri saveFileURI) {
//        Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        return mIntent.putExtra(MediaStore.EXTRA_OUTPUT, saveFileURI);
//    }
//
//    /**
//     * ��ȡ[��ת���ü�����,Ĭ�Ͽ�����]��Intent
//     */
//    public static Intent getCropImageIntent(int outputX, int outputY, Uri fromFileURI,
//                                            Uri saveFileURI) {
//        return getCropImageIntent(1, 1, outputX, outputY, true, fromFileURI, saveFileURI);
//    }
//
//    /**
//     * ��ȡ[��ת���ü�����,Ĭ�Ͽ�����]��Intent
//     */
//    public static Intent getCropImageIntent(int aspectX, int aspectY, int outputX, int outputY, Uri fromFileURI,
//                                            Uri saveFileURI) {
//        return getCropImageIntent(aspectX, aspectY, outputX, outputY, true, fromFileURI, saveFileURI);
//    }
//
//
//    /**
//     * ��ȡ[��ת���ü�����]��Intent
//     */
//    public static Intent getCropImageIntent(int aspectX, int aspectY, int outputX, int outputY, boolean canScale,
//                                            Uri fromFileURI, Uri saveFileURI) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(fromFileURI, "image/*");
//        intent.putExtra("crop", "true");
//        // X�����ϵı���
//        intent.putExtra("aspectX", aspectX <= 0 ? 1 : aspectX);
//        // Y�����ϵı���
//        intent.putExtra("aspectY", aspectY <= 0 ? 1 : aspectY);
//        intent.putExtra("outputX", outputX);
//        intent.putExtra("outputY", outputY);
//        intent.putExtra("scale", canScale);
//        // ͼƬ���ò���ڱ߽��
//        intent.putExtra("scaleUpIfNeeded", true);
//        intent.putExtra("return-data", false);
//        // ��Ҫ����ȡ���ļ�·���Ͳü�д���·�����֣����������ļ�0byte
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, saveFileURI);
//        // true-->�����������Ϳ�������ΪBitmap�����ǲ��ܴ���̫�󣬽ش�ͼ��URI��Сͼ��Bitmap����ȫ��ʹ��URI
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        // ȡ������ʶ����
//        intent.putExtra("noFaceDetection", true);
//        return intent;
//    }
//
//    /**
//     * ���ѡ������ͼƬ
//     *
//     * @param context ������
//     * @param data    onActivityResult���ص�Intent
//     * @return bitmap
//     */
//    public static Bitmap getChoosedImage(Activity context, Intent data) {
//        if (data == null) return null;
//        Bitmap bm = null;
//        ContentResolver cr = context.getContentResolver();
//        Uri originalUri = data.getData();
//        try {
//            bm = MediaStore.Images.Media.getBitmap(cr, originalUri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bm;
//    }
//
//    /**
//     * ���ѡ������ͼƬ·��
//     *
//     * @param context ������
//     * @param data    onActivityResult���ص�Intent
//     * @return
//     */
//    public static String getChoosedImagePath(Activity context, Intent data) {
//        if (data == null) return null;
//        String path = "";
//        ContentResolver resolver = context.getContentResolver();
//        Uri originalUri = data.getData();
//        if (null == originalUri) return null;
//        String[] projection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = resolver.query(originalUri, projection, null, null, null);
//        if (null != cursor) {
//            try {
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                cursor.moveToFirst();
//                path = cursor.getString(column_index);
//            } catch (IllegalArgumentException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (!cursor.isClosed()) {
//                        cursor.close();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return StringUtils.isEmpty(path) ? originalUri.getPath() : null;
//    }
//
//    /**
//     * ��ȡ����֮�����Ƭ�ļ���JPG��ʽ��
//     *
//     * @param data     onActivityResult�ص����ص�����
//     * @param filePath �ļ�·��
//     * @return �ļ�
//     */
//    public static File getTakePictureFile(Intent data, String filePath) {
//        if (data == null) return null;
//        Bundle extras = data.getExtras();
//        if (extras == null) return null;
//        Bitmap photo = extras.getParcelable("data");
//        File file = new File(filePath);
//        if (ImageUtils.save(photo, file, Bitmap.CompressFormat.JPEG)) return file;
//        return null;
//    }
}
