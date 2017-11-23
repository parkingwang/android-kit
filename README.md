# Swiss Android - Android常用工具库

### SwDoubleClick - 双击退出App

```java
public class MainActivity extends AppCompatActivity {

    private final SwDoubleClick mDoubleClick = new SwDoubleClick(this, "再按一次原地爆炸")
            .setOnDoubleClickHandler(new SwDoubleClick.OnDoubleClickHandler() {
                @Override
                public void onDoubleClick() {
                    // 默认实现为关闭Activity
                    // MainActivity.this.finish();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDoubleClick.onKeyDown(keyCode, event) ||
                super.onKeyDown(keyCode, event);
    }
}
```

### SwLoading - 一款Loading弹窗口组件

// TODO

### SwToast - 一款Toast提示组件

支持 Tip, Success, Failed, Warning 三个提示类型；

----

## 依赖

````gradle
repositories {
    maven { url "https://dl.bintray.com/parkingwang/maven" }
}

dependencies {
    compile 'com.parkingwang:swiss-android:0.0.3-ALPHA'
}

```