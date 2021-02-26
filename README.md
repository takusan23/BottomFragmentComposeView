🤔
```java
java.lang.IllegalStateException: ViewTreeLifecycleOwner not found from DecorView@b98b3fc[MainActivity]
```

🥳🎉
```kotlin
class BottomFragmentCompose : BottomSheetDialogFragment() {

    @InternalComposeUiApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {

            ViewTreeLifecycleOwner.set(this, viewLifecycleOwner)
            val newRecomposer = AtomicReference(WindowRecomposerFactory.LifecycleAware).get().createRecomposer(rootView)
            compositionContext = newRecomposer

            setContent {
                Text(
                    text = "BottomSheetDialogFragment + ComposeView",
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }

}
```

詳しくは  
https://takusan.negitoro.dev/posts/android_bottom_fragment_use_compose_view/
