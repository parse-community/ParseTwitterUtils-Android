/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.oauth;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * For internal use.
 */
public class OAuth1FlowDialog extends Dialog {
  private static final FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(
      ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
  private final String callbackUrl;
  private final String requestUrl;
  private final String serviceUrlIdentifier;
  private final FlowResultHandler handler;
  private ProgressDialog progressDialog;
  private ImageView closeImage;
  private WebView webView;
  private FrameLayout content;

  public OAuth1FlowDialog(Context context, String requestUrl, String callbackUrl,
      String serviceUrlIdentifier, FlowResultHandler resultHandler) {
    super(context, android.R.style.Theme_Translucent_NoTitleBar);
    this.requestUrl = requestUrl;
    this.callbackUrl = callbackUrl;
    this.serviceUrlIdentifier = serviceUrlIdentifier;
    this.handler = resultHandler;

    this.setOnCancelListener(new OnCancelListener() {
      @Override
      public void onCancel(DialogInterface dialog) {
        handler.onCancel();
      }
    });
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    progressDialog = new ProgressDialog(getContext());
    progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    progressDialog.setMessage("Loading...");

    requestWindowFeature(Window.FEATURE_NO_TITLE);
    content = new FrameLayout(getContext());

    createCloseImage();

    // Use the close image to determine the margin for the WebView.
    int webViewMargin = closeImage.getDrawable().getIntrinsicWidth() / 2;
    setUpWebView(webViewMargin);

    // Now that the WebView has been added, add the Close image.
    content.addView(closeImage, new LayoutParams(LayoutParams.WRAP_CONTENT,
        LayoutParams.WRAP_CONTENT));
    addContentView(content, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
  }

  private void createCloseImage() {
    closeImage = new ImageView(getContext());
    // Dismiss the dialog when user click on the 'x'
    closeImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        OAuth1FlowDialog.this.cancel();
      }
    });
    Drawable closeDrawable = getContext().getResources().getDrawable(android.R.drawable.btn_dialog);
    closeImage.setImageDrawable(closeDrawable);

    // Make the close image invisible until the WebView has loaded.
    closeImage.setVisibility(View.INVISIBLE);
  }

  private void setUpWebView(int margin) {
    LinearLayout webViewContainer = new LinearLayout(getContext());
    webView = new WebView(getContext());
    webView.setVerticalScrollBarEnabled(false);
    webView.setHorizontalScrollBarEnabled(false);
    webView.setWebViewClient(new OAuth1WebViewClient());
    webView.getSettings().setJavaScriptEnabled(true);
    webView.loadUrl(requestUrl);
    webView.setLayoutParams(FILL);
    webView.setVisibility(View.INVISIBLE);

    webViewContainer.setPadding(margin, margin, margin, margin);
    webViewContainer.addView(webView);
    content.addView(webViewContainer);
  }

  public interface FlowResultHandler {
    /**
     * Called when the user cancels the dialog.
     */
    void onCancel();

    /**
     * Called when the dialog's web view receives an error.
     */
    void onError(int errorCode, String description, String failingUrl);

    /**
     * Called when the dialog portion of the flow is complete.
     * 
     * @param callbackUrl
     *          The final URL called back (including any query string appended
     *          by the server).
     */
    void onComplete(String callbackUrl);
  }

  private class OAuth1WebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
      if (url.startsWith(callbackUrl)) {
        OAuth1FlowDialog.this.dismiss();
        handler.onComplete(url);
        return true;
      } else if (url.contains(serviceUrlIdentifier)) {
        return false;
      }
      // launch non-service URLs in a full browser
      getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
      return true;
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
      super.onReceivedError(view, errorCode, description, failingUrl);
      OAuth1FlowDialog.this.dismiss();
      handler.onError(errorCode, description, failingUrl);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
      super.onPageStarted(view, url, favicon);
      progressDialog.show();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);
      try {
        progressDialog.dismiss();
      } catch (IllegalArgumentException e) {
        // If the view is not attached to the window manager (can happen
        // if the "home" button is pressed at just the right moment),
        // fail without crashing.
      }
      /*
       * Once webview is fully loaded, set the mContent background to be
       * transparent and make visible the 'x' image.
       */
      content.setBackgroundColor(Color.TRANSPARENT);
      webView.setVisibility(View.VISIBLE);
      closeImage.setVisibility(View.VISIBLE);
    }
  }
}
