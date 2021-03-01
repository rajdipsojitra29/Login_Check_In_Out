//
//  SwiftFunctionsToCallInReactNativeModuleBridge.m
//  Login_Check_In_Out
//
//  Created by ks on 25/02/21.
//

#import <Foundation/Foundation.h>
#import "UIKit/UIKit.h"
#import <React/RCTBridgeModule.h>


@interface RCT_EXTERN_MODULE(SwiftFunctionsToCallInReactNativeModule, NSObject)

RCT_EXTERN_METHOD(buttonActionEnroll:(NSString *)stringFromReactNative callbackSuccess:(RCTResponseSenderBlock)callbackSuccess callbackError:(RCTResponseSenderBlock)callbackError)


@end
