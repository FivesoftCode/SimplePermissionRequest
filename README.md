# SimplePermissionRequest

Request app permissions easily.

## Usage 

Implementation:

    implementation 'com.github.FivesoftCode:SimplePermissionRequest:1.0.2'

Check permission:

    if(PermissionRequest.isPermissionGranted(Manifest.permission.CAMERA, this)){
       //You have permission
    } else {
       //Permission not granted
    }
    
Request permission:

    PermissionRequest.from(this)
          .setPermission(Manifest.permission.CAMERA)
          .setListener(isGranted -> {
              if(isGranted){
                  //Permission is granted.
              } else {
                  //Permission denied.
              }
          }).request();
          
## License

    The MIT License (MIT)

    Copyright (c) 2021 FivesoftCode

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
    
## Check my apps in Google Play!

<a href = "https://play.google.com/store/apps/details?id=com.fivesoft.sheetofpaper">Sheet of Paper</a><br>
<a href = "https://play.google.com/store/apps/details?id=com.fivesoft.blackboard">Blackboard</a>
