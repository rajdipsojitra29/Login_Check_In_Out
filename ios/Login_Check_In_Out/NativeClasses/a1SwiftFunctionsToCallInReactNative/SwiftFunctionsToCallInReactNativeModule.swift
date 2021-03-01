//
//  SwiftFunctionsToCallInReactNativeModule.swift
//  Login_Check_In_Out
//
//  Created by ks on 25/02/21.
//

import UIKit
import MobileCoreServices


@objc(SwiftFunctionsToCallInReactNativeModule)
class SwiftFunctionsToCallInReactNativeModule: NSObject {

  let rootViewControllerObject = UIApplication.shared.keyWindow?.rootViewController
  var callbackLogin: RCTResponseSenderBlock?
  
  
  
  
  
  
  
  
  
  
  
  @objc func buttonActionEnroll(_ stringFromReactNative:String, callbackSuccess:@escaping RCTResponseSenderBlock, callbackError:@escaping RCTResponseSenderBlock) -> Void {
    print("\(stringFromReactNative)")
    
    self.callbackLogin = callbackSuccess
    
    DispatchQueue.main.async {
      //      let rootViewControllerObject = UIApplication.shared.keyWindow?.rootViewController
      let viewControllerObject = UIStoryboard(name: "VideoRecordS", bundle:nil).instantiateViewController(withIdentifier: VideoRecordVC.stringClassName) as! VideoRecordVC
      //        viewControllerObject.delegate = self
      viewControllerObject.stringPreviousVCName = self.stringClassName
      viewControllerObject.viewControllerObjectTemp = self.rootViewControllerObject
      viewControllerObject.callback = callbackSuccess
      viewControllerObject.view.frame = CGRect(x: 0, y: 0, width: 100, height: DEVICE_HEIGHT)
      self.rootViewControllerObject?.addChild(viewControllerObject)
      self.rootViewControllerObject?.view.addSubview(viewControllerObject.view)
      viewControllerObject.didMove(toParent: self.rootViewControllerObject)
      UIView.animate(withDuration: 0.3, animations: {
        viewControllerObject.view.frame = CGRect(x: 0, y: 0, width: DEVICE_WIDTH, height: DEVICE_HEIGHT)
      })
    }
   
    /*
    //    At a time only one Callback can invoke
    self.callbackLogin!(["",""]); //callbackSuccess(["", ""]);
    //callbackError(["", ""]);
    */
  }
  
  
  
  
  
}
