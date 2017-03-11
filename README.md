# KotlinRxJava2Interop
[![](https://jitpack.io/v/milk-machine/KotlinRxJava2Interop.svg)](https://jitpack.io/#milk-machine/KotlinRxJava2Interop)

It's "copy-paste" from https://github.com/akarnokd/RxJava2Interop with changes in Kotlin style (with extension functions).

## Example
Observable (1v) -> Observable (2v)
```
	Observable.just(1,2,3,4)
		.toV2Observable()
		.subscribe()
```

## Download
Add to `app` `build.gradle`:
```
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```

Add to `module` `build.gradle`:
```
dependencies {
	compile 'com.github.milk-machine:KotlinRxJava2Interop:0.9.3-rc1'
}
```
