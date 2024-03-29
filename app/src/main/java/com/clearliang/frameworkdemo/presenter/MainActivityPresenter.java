package com.clearliang.frameworkdemo.presenter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.clearliang.frameworkdemo.model.bean.LoginBean;
import com.clearliang.frameworkdemo.view.base.BaseInterface;
import com.clearliang.frameworkdemo.view.base.BasePresenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Subscriber;

/**
 * Created by 99794 on 2018/4/13.
 */

public class MainActivityPresenter extends BasePresenter<MainActivityPresenter.MainActivityInterface> {
    private MainActivityInterface mainActivityInterface;

    public MainActivityPresenter(MainActivityInterface mainActivityInterface) {
        this.mainActivityInterface = mainActivityInterface;
    }

    public interface MainActivityInterface extends BaseInterface {

    }

    public void showInputDialog(final Activity activity, final Bitmap bitmap) {
        AlertDialog.Builder normalDialog;
        normalDialog = new AlertDialog.Builder(activity);
        normalDialog.setTitle("请输入图片名");
        final EditText editText = new EditText(activity);
        normalDialog.setView(editText);
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    savaScanPicture(activity, bitmap, editText.getText().toString());
                    Toast.makeText(activity, "保存成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "请输入图片名", Toast.LENGTH_SHORT).show();
                }
            }
        });
        normalDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(activity, "取消操作", Toast.LENGTH_SHORT).show();
            }
        });
        normalDialog.show();
    }

    private void savaScanPicture(Activity activity, Bitmap bmp, String picName) {
        String fileName = null;
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;

        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;

        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName + ".jpg");

            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
            }

        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //通知相册更新
        MediaStore.Images.Media.insertImage(activity.getContentResolver(), bmp, fileName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        activity.sendBroadcast(intent);
    }

}
