# PUToast
Toast 组件默认只能展示在主 Display 上，PUToast 通过构造一个 PopupWindoww 在 Presentation 界面上模拟 Toast 的显示与隐藏。

## 使用
使用方法参照 app module 的 `TestPresentation`。
1. 构造 PUToast 对象
选择文字或自定义 View 进行构造
```
    /**
     * 显示文字
     */
    public PUToast(Presentation dialog, String text);

    /**
     * 显示自定义view
     */
    public PUToast(Presentation dialog, View view);
```
2. 修改 TextView 样式
调用 `PUToast#getTextView`方法获取`TextView`对象后修改属性
```
    /**
     * 获取当前的TextView，用于自定义TextView样式
     * @return 当前contentView为TextView时返回对象，否则返回null
     */
    public TextView getTextView();
```
3. 展示
```
    /**
     * 展示一秒
     */
    public void show();

    /**
     * 展示两秒
     */
    public void showLong();
```

## 效果
- 默认样式
![默认样式][img1]

- 修改 TextView 样式
![修改文本样式][img2]

- 传入自定义 View
![显示Icon][img3]

[img1]:./imgs/1.jpg
[img2]:./imgs/2.jpg
[img3]:./imgs/3.jpg