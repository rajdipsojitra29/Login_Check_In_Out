//
//  UtilitySwift.swift
//  Login_Check_In_Out
//
//  Created by ks on 25/02/21.
//

import UIKit
import Toast_Swift
import Photos
import SystemConfiguration


class UtilitySwift: NSObject {

  public static var mBProgressHUD = MBProgressHUD()
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  @objc class func showAlertControllerOrNotWithOKButton(_ vcObject:UIViewController, _ title:String, _ message:String, _ isShowAlert:Bool, completionOKButton:@escaping () -> Void) {
    if isShowAlert {
      DispatchQueue.main.async {
        var topController = UIViewController()
        if (vcObject.isEqual(nil)) {
          topController = UIApplication.shared.keyWindow!.rootViewController! as UIViewController
          while ((topController.presentedViewController) != nil) {
            topController = topController.presentedViewController!
          }
        }
        else {
          topController = vcObject
        }
        
        let alertController = UIAlertController(title:title, message:message, preferredStyle:UIAlertController.Style.alert)
        
        let alertActionOk: UIAlertAction = UIAlertAction(title:"OK", style:.default) { action -> Void in
          print("OK")
          completionOKButton()
        }
        alertController.addAction(alertActionOk)
        topController.present(alertController, animated:true, completion:{
        })
      }
    }
  }
  
  
  @objc class func showAlertControllerOrNotWithOKButton(_ title:String, _ message:String, _ isShowAlert:Bool, completionOKButton:@escaping () -> Void) {
    if isShowAlert {
      DispatchQueue.main.async {
        var topController = UIApplication.shared.keyWindow!.rootViewController! as UIViewController
        while ((topController.presentedViewController) != nil) {
          topController = topController.presentedViewController!
        }
        let alertController = UIAlertController(title:title, message:message, preferredStyle:UIAlertController.Style.alert)
        
        let alertActionOk: UIAlertAction = UIAlertAction(title:"OK", style:.default) { action -> Void in
          print("OK")
          completionOKButton()
        }
        alertController.addAction(alertActionOk)
        topController.present(alertController, animated:true, completion:{
        })
      }
    }
  }
  
  
  @objc class func showMBProgressHUD() {
    DispatchQueue.main.async {
      var viewObj = UIView()
      var topController = UIApplication.shared.keyWindow!.rootViewController!
      while ((topController.presentedViewController) != nil) {
        topController = topController.presentedViewController!
      }
      viewObj = topController.view
      
      mBProgressHUD = MBProgressHUD.showAdded(to:viewObj, animated:true)
      
      mBProgressHUD.backgroundView.style = MBProgressHUDBackgroundStyle.solidColor
      mBProgressHUD.backgroundView.color = UIColor(white:0.0, alpha:0.2)
      
      mBProgressHUD.label.text = STRING_LOADING_MESSAGE
      // mBProgressHUD.label.font = [UIFont italicSystemFontOfSize:16.f];
    }
  }
  
  
  @objc class func hideMBProgressHUD() {
    DispatchQueue.main.async {
      mBProgressHUD.hide(animated:true)
    }
  }
  
  
  @objc class func showToast(_ stringMessage:String) {
    DispatchQueue.main.async {
      var viewObj = UIView()
      var topController = UIApplication.shared.keyWindow!.rootViewController!
      while ((topController.presentedViewController) != nil) {
        topController = topController.presentedViewController!
      }
      viewObj = topController.view
      
      var styleToast = ToastStyle()
      styleToast.titleAlignment = .center
      styleToast.messageAlignment = .center
      viewObj.makeToast(stringMessage, duration:3.0, position:.center, style: styleToast)
    }
  }
  
  
  @objc class func showAlertControllerOrNotWithOK_CancelButton(_ title:String, _ message:String, _ isShowAlert:Bool, completionOKButton:@escaping () -> Void, completionCancelButton:@escaping () -> Void) {
     if (isShowAlert) {
       DispatchQueue.main.async {
         var topController = UIApplication.shared.keyWindow!.rootViewController! as UIViewController
         while ((topController.presentedViewController) != nil) {
           topController = topController.presentedViewController!
         }
         let alertController = UIAlertController(title:title, message:message, preferredStyle:UIAlertController.Style.alert)
         
         let alertActionCancel: UIAlertAction = UIAlertAction(title:"Cancel", style:.default) { action -> Void in
           //          print("Cancel")
           completionOKButton()
         }
         alertController.addAction(alertActionCancel)
         
         let alertActionOk: UIAlertAction = UIAlertAction(title:"OK", style:.default) { action -> Void in
           //          print("OK")
           completionCancelButton()
         }
         alertController.addAction(alertActionOk)
         
         topController.present(alertController, animated:true, completion:{
         })
       }
     }
   }
  
  
  @objc class func checkPhotoLibraryPermission(_ completionSuccess:@escaping () -> Void, completionFailure:@escaping () -> Void) {
    //    Info.plist file add permission "Privacy - Photo Library Usage Description"
    let authorizationStatus = PHPhotoLibrary.authorizationStatus()
    if (authorizationStatus == .authorized) {
      //      print("Access has been granted")
      completionSuccess()
    }
    else if (authorizationStatus == .denied) {
      //      print("Access has been denied")
      UtilitySwift.showAlertControllerOrNotWithOK_CancelButton("Please give Photos permission for " + UtilitySwift.getApplicationName(), "", true, completionOKButton: {
        //        print("Cancel")
      }) {
        //        print("OK")
        UIApplication.shared.open(URL(string: UIApplication.openSettingsURLString)!)
      }
    }
    else if (authorizationStatus == .notDetermined) {
      //      print("Access has not been determined")
      PHPhotoLibrary.requestAuthorization({ status in
        if (status == .authorized) {
          //              print("Access has been granted")
          completionSuccess()
        }
        else {
          //              print("Access has been denied")
          completionFailure()
        }
      })
    }
    else if (authorizationStatus == .restricted) {
      //      print("Restricted access - normally won't happen.")
      completionFailure()
    }
  }
  
  
  @objc class func getApplicationName() -> String {
    return Bundle.main.object(forInfoDictionaryKey: "CFBundleName") as? String ?? ""
  }
  
  
  @objc class func getBase64FromPDFVideoURL(_ url: URL) -> String {
    var stringPDFVideoInBase64 = String("")
    do {
      let fileData = try Data.init(contentsOf: url)
      stringPDFVideoInBase64 = fileData.base64EncodedString(options: NSData.Base64EncodingOptions.init(rawValue: 0))
    } catch {
      stringPDFVideoInBase64 = ""
    }
    return stringPDFVideoInBase64
  }
  
  
  @objc class func  saveVideoInGalleryAfterDownloadFromURL(_ urlFilePath:URL, completionHandler:@escaping(_ isSavedFile:Bool) -> Void) {
    //This method(saveVideoInGalleryAfterDownloadFromURL) will work in some video and will not work in some video
    /*
     //Call this method Start
     var requestAlamofire: Alamofire.Request?
     //    requestAlamofire?.cancel()
     
     var mBProgressHUD: MBProgressHUD?
     UtilitySwift.showMBProgressHUDWithProgressCircle(completionHandlerMain: { (mBProgressHUDTemp) in
     mBProgressHUD = mBProgressHUDTemp
     }) {
     requestAlamofire?.cancel()
     }
     
     requestAlamofire = Alamofire.request(stringFromReactNative).downloadProgress(closure : { (progress) in
     print(progress.fractionCompleted)
     if (mBProgressHUD != nil) {
     mBProgressHUD?.progress = Float(progress.fractionCompleted)
     }
     }).responseData{ (response) in
     print(response)
     print(response.result.value ?? "")
     print(response.result.description)
     
     UtilitySwift.hideMBProgressHUD()
     
     if (response.result.description.elementsEqual("SUCCESS")) {
     if let data = response.result.value {
     
     let stringFileName = UtilitySwift.getLastWordWithExtensionFromStringURL(stringFromReactNative)
     
     let urlDocumentDirectoryFileManager = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first!
     let urlFile = urlDocumentDirectoryFileManager.appendingPathComponent(stringFileName)
     print(urlFile)
     
     do {
     try data.write(to: urlFile, options: .atomic)
     } catch {
     UtilitySwift.showToast(STRING_DEFAULT_ERROR_MESSAGE)
     }
     
     UtilitySwift.saveVideoInGalleryAfterDownloadFromURL(urlFile) { (isSavedFile) in
     print(isSavedFile)
     }
     }
     }
     }
     //Call this method End
     */
    
    print(urlFilePath)
    self.checkPhotoLibraryPermission({
      PHPhotoLibrary.shared().performChanges({
        PHAssetChangeRequest.creationRequestForAssetFromVideo(atFileURL:urlFilePath)
      }) { (saved, error) in
        print(saved)
        if (saved) {
          //        UtilitySwift.showToast("Your video was successfully saved")
        }
        else {
          print("error = " + error!.localizedDescription)
          UtilitySwift.showToast(STRING_DEFAULT_ERROR_MESSAGE)
        }
        completionHandler(saved)
      }
    }) {
      //      print("Access no given or denied")
    }
  }
  
  
  @objc class func showAlertControllerOrNotWithOKButtonActionCompletionHandler(_ stringTitle: String, _ stringMessage: String, _ boolShowAlert: Bool, completionHandlerForOKButtonAction: @escaping () -> Void) {
    if boolShowAlert {
      var topController = UIApplication.shared.keyWindow!.rootViewController! as UIViewController
      while ((topController.presentedViewController) != nil){
        topController = topController.presentedViewController!
      }
      let alertController = UIAlertController(title: stringTitle, message: stringMessage, preferredStyle: UIAlertController.Style.alert)
      
      let alertActionOk: UIAlertAction = UIAlertAction(title: "OK", style: .default) { action -> Void in
        //Just dismiss the action sheet
        print("OK")
        completionHandlerForOKButtonAction()
      }
      alertController.addAction(alertActionOk)
      topController.present(alertController, animated:true, completion:nil)
    }
  }
  
  
  @objc class func isInternetConnected(isShowPopup: Bool) -> Bool {
    var zeroAddress = sockaddr_in()
    zeroAddress.sin_len = UInt8(MemoryLayout.size(ofValue: zeroAddress))
    zeroAddress.sin_family = sa_family_t(AF_INET)
    
    let defaultRouteReachability = withUnsafePointer(to: &zeroAddress) {
      $0.withMemoryRebound(to: sockaddr.self, capacity: 1) {zeroSockAddress in
        SCNetworkReachabilityCreateWithAddress(nil, zeroSockAddress)
      }
    }
    var flags = SCNetworkReachabilityFlags()
    if !SCNetworkReachabilityGetFlags(defaultRouteReachability!, &flags) {
      if (isShowPopup) {
        self.showAlertControllerOrNotWithOKButtonActionCompletionHandler(STRING_CHECK_INTERNET_TITLE, STRING_CHECK_INTERNET_MESSAGE, true, completionHandlerForOKButtonAction: {
        })
      }
      return false
    }
    let isReachable = (flags.rawValue & UInt32(kSCNetworkFlagsReachable)) != 0
    let needsConnection = (flags.rawValue & UInt32(kSCNetworkFlagsConnectionRequired)) != 0
    
    if (isReachable && !needsConnection) == false{
      if (isShowPopup) {
        self.showAlertControllerOrNotWithOKButtonActionCompletionHandler(STRING_CHECK_INTERNET_TITLE, STRING_CHECK_INTERNET_MESSAGE, true, completionHandlerForOKButtonAction: {
        })
      }
    }
    return (isReachable && !needsConnection)
  }
  
}
