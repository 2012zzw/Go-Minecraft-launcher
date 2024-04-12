package com.zzw.gmcl;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Web {

    public static String renderWebPage(String url,boolean is_login_web) {
        Platform.startup(() -> {
            // ���� Stage
            Stage stage = new Stage();
            stage.setTitle("Web Renderer");

            // ���� WebView ���
            WebView webView = new WebView();
            // ����ָ�� URL
            webView.getEngine().load(url);

            // ���� Scene������ WebView ��ӵ�����
            Scene scene = new Scene(webView, 800, 600);

            // ���� WebView �ļ���״̬
            webView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == Worker.State.SUCCEEDED) {
                    // ��ȡ URL �е� code ����
                    String extractedCode = extractCode(webView.getEngine().getLocation());
                    if (extractedCode != null) {
                        System.out.println("Code: " + extractedCode);
                        login.code=extractedCode;
                        // ������Խ��н�һ���������罫 code �������ݸ���������
                        stage.close();
                    }
                }
            });

            // ���� Scene������ʾ Stage
            stage.setScene(scene);
            stage.show();
        });
        return "";
    }
    // ��ȡ URL �е� code �����ķ���
    private static String extractCode(String url) {
        Pattern pattern = Pattern.compile("\\bcode=([^&]+)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
}

