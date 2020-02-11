# MindCache
Image loading and cache library


## Installation

### Configure jitpack in gradle
```
allprojects {
 repositories {
    jcenter()
    maven { url "https://jitpack.io" }
 }
}
```


### Add library to gradle app level and Sync
```
dependencies {
	        implementation 'com.github.EverK777:MindCache:Tag'
	}
 ```
### Get image from url 

 ```
profile_image.loadImage("https://imageDummy.jpg")
 ```
 

### download a file (it needs a view)
```
  GlobalScope.launch {
          val  file =  downloadFileButton.downloadAndGetFile("https://dummyfile.pdf")
        }
```        

 ### Cancel download 
 
```
profile_image.cancelLoading()
 ```
        

