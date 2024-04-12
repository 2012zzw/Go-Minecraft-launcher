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
            // 创建 Stage
            Stage stage = new Stage();
            stage.setTitle("Web Renderer");

            // 创建 WebView 组件
            WebView webView = new WebView();
            // 加载指定 URL
            webView.getEngine().load(url);

            // 创建 Scene，并将 WebView 添加到其中
            Scene scene = new Scene(webView, 800, 600);

            // 监听 WebView 的加载状态
            webView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == Worker.State.SUCCEEDED) {
                    // 提取 URL 中的 code 参数
                    String extractedCode = extractCode(webView.getEngine().getLocation());
                    if (extractedCode != null) {
                        System.out.println("Code: " + extractedCode);
                        login.code=extractedCode;
                        // 这里可以进行进一步处理，例如将 code 参数传递给其他方法
                        stage.close();
                    }
                }
            });

            // 设置 Scene，并显示 Stage
            stage.setScene(scene);
            stage.show();
        });
        return "";
    }
    // 提取 URL 中的 code 参数的方法
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

