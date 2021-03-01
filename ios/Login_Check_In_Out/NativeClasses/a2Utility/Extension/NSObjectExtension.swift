//
//  NSObjectExtension.swift
//  Login_Check_In_Out
//
//  Created by ks on 25/02/21.
//

import Foundation


extension NSObject {
    var stringClassName: String {
        return String(describing: type(of: self))
    }

    class var stringClassName: String {
        return String(describing: self)
    }
}
