//
//  VideoRecordVC.swift
//  Login_Check_In_Out
//
//  Created by ks on 25/02/21.
//

import UIKit
import AVFoundation


class VideoRecordVC: UIViewController {
  
  var stringPreviousVCName = String("")
  var viewControllerObjectTemp: UIViewController?
  var callback: RCTResponseSenderBlock?
  
  @IBOutlet weak var camPreview: UIView!
  
  let captureSession = AVCaptureSession()
  let movieOutput = AVCaptureMovieFileOutput()
  var previewLayer: AVCaptureVideoPreviewLayer!
  var activeInput: AVCaptureDeviceInput!
  var outputURL: URL!
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  override func viewDidLoad() {
    super.viewDidLoad()
    self.viewWillAppear(true)
  }
  
  
  override func viewWillAppear(_ animated: Bool) {
    super.viewWillAppear(true)
    //        UtilityObjectiveC.setAnimationOn(self.navigationController!)
    self.viewDidAppear(true)
  }
  
  
  override func viewDidAppear(_ animated: Bool) {
    super.viewDidAppear(true)
    self.customizeNavigationBar()
    
    if setupSession() {
      setupPreview()
      startSession()
    }
    
    DispatchQueue.main.asyncAfter(deadline: .now() + 1.0) { //Time in Second
      self.startRecording()
      DispatchQueue.main.asyncAfter(deadline: .now() + 13.0) { //13.0 //Time in Second
        self.stopRecording()
      }
    }
  }
  
  
  override func viewWillDisappear(_ animated: Bool) {
    super.viewWillDisappear(animated)
    self.captureSession.stopRunning()
  }
  
  
  override func viewDidDisappear(_ animated: Bool) {
    super.viewDidDisappear(true)
    if (self.view.window == nil) {
      //            self.view = nil
    }
  }
  
  
  override func didReceiveMemoryWarning() {
    super.didReceiveMemoryWarning()
    // Dispose of any resources that can be recreated.
  }
  
  
  /*
   // MARK: - Navigation
   
   // In a storyboard-based application, you will often want to do a little preparation before navigation
   override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
   // Get the new view controller using segue.destination.
   // Pass the selected object to the new view controller.
   }
   */
  
  
  func customizeNavigationBar() {
    //          let statusBar: UIView = UIApplication.shared.value(forKey: "statusBar") as! UIView
    //          statusBar.backgroundColor = UtilitySwift.hexStringToUIColor(hex: STRING_HEX_UICOLOR_APP_THEAM)
    let navigationBarObject = self.navigationController?.navigationBar
    //    navigationBarObject?.barTintColor = UtilitySwift.hexStringToUIColor(hex: STRING_HEX_UICOLOR_APP_THEAM)
    navigationBarObject?.tintColor = UIColor.white
    navigationBarObject?.titleTextAttributes = [NSAttributedString.Key.foregroundColor:UIColor.white]
    
    self.navigationController?.navigationBar.isTranslucent = false
    self.navigationController?.isNavigationBarHidden = true
    
    self.navigationItem.title = ""
    self.navigationItem.hidesBackButton = true
  }
  
  
  func removeCurrentViewFromParentViewController() {
    let viewControllerObject = self.viewControllerObjectTemp?.children.last
    viewControllerObject?.view.removeFromSuperview()
    viewControllerObject?.removeFromParent()
  }
  
  
  //MARK:- Button Action
  @IBAction func buttonActionBack(_ sender: Any) {
    /*
     var buttonObj = sender as! UIButton
     buttonObj.setTitle("shreeji1", for: .normal)
     */
    
    //        if UtilitySwift.isInternetConnected(isShowPopup: true) {
    //    callback!(["",""]);
    self.navigationController?.isNavigationBarHidden = true
    self.removeCurrentViewFromParentViewController()
    //        }
  }
  
  /*
   @IBAction func buttonActionButton1(_ sender: Any) {
   //    callback!(["this is shreeji from VR",""]);
   //    self.buttonActionBack("")
   
   startRecording()
   DispatchQueue.main.asyncAfter(deadline: .now() + 3.0) { //13.0 //Time in Second
   self.stopRecording()
   }
   }
   */
  
  func setupPreview() {
    // Configure previewLayer
    previewLayer = AVCaptureVideoPreviewLayer(session: captureSession)
    previewLayer.frame = camPreview.bounds //only resize preview not video dimensions
//    previewLayer.frame = CGRect(x: 0, y: 0, width: 100, height: 100) //only resize preview not video dimensions
    
    previewLayer.videoGravity = AVLayerVideoGravity.resizeAspectFill
//    previewLayer.videoGravity = AVLayerVideoGravity.resizeAspect
//    previewLayer.videoGravity = AVLayerVideoGravity.resize
    camPreview.layer.addSublayer(previewLayer)
  }
  
  
  //MARK:- Setup Camera
  func setupSession() -> Bool {
//    captureSession.sessionPreset = AVCaptureSession.Preset.high
//    captureSession.sessionPreset = AVCaptureSession.Preset.low //dimensions = 192x144, default FPS = 7
//    captureSession.sessionPreset = AVCaptureSession.Preset.medium //dimensions = 480x360, default FPS = 25
    captureSession.sessionPreset = AVCaptureSession.Preset.vga640x480 //dimensions = 640x480, default FPS = 25
    
    
    // Setup Camera
//    var camera = AVCaptureDevice.default(for: AVMediaType.video)!
    var camera = AVCaptureDevice.default(.builtInWideAngleCamera, for: AVMediaType.video, position: .front)
    camera!.configureDesiredFrameRate(desiredFrameRateMin: 5, desiredFrameRateMax: 10) //This will priorities desiredFrameRateMin for video FPS if it's support //For back camera video FPS result is = 5 & For front camera video FPS result is = 25
//    camera = getDevice(position: .front)! //not working activeVideoMinFrameDuration, activeVideoMaxFrameDuration got vide FPS for front camera
//    camera = getDevice(position: .back)! //Working activeVideoMinFrameDuration, activeVideoMaxFrameDuration got vide FPS for back camera
    
    do {
      let input = try AVCaptureDeviceInput(device: camera!)
      if captureSession.canAddInput(input) {
        captureSession.addInput(input)
        activeInput = input
      }
    } catch {
      print("Error setting device video input: \(error)")
      return false
    }
    
    // Setup Microphone
    let microphone = AVCaptureDevice.default(for: AVMediaType.audio)!
    do {
      let micInput = try AVCaptureDeviceInput(device: microphone)
      if captureSession.canAddInput(micInput) {
        captureSession.addInput(micInput)
      }
    } catch {
      print("Error setting device audio input: \(error)")
      return false
    }
    
    // Movie output
    if captureSession.canAddOutput(movieOutput) {
      captureSession.addOutput(movieOutput)
    }
    return true
  }
  
  func setupCaptureMode(_ mode: Int) {
    // Video Mode
  }
  
  
  //MARK:- Camera Session
  func startSession() {
    if !captureSession.isRunning {
      videoQueue().async {
        self.captureSession.startRunning()
      }
    }
  }
  
  
  func stopSession() {
    if captureSession.isRunning {
      videoQueue().async {
        self.captureSession.stopRunning()
      }
    }
  }
  
  
  func videoQueue() -> DispatchQueue {
    return DispatchQueue.main
  }
  
  
  func currentVideoOrientation() -> AVCaptureVideoOrientation {
    var orientation: AVCaptureVideoOrientation
    switch UIDevice.current.orientation {
    case .portrait:
      orientation = AVCaptureVideoOrientation.portrait
    case .landscapeRight:
      orientation = AVCaptureVideoOrientation.landscapeLeft
    case .portraitUpsideDown:
      orientation = AVCaptureVideoOrientation.portraitUpsideDown
    default:
      orientation = AVCaptureVideoOrientation.landscapeRight
    }
    
    return orientation
  }
  
  
  func tempURL() -> URL? {
    let directory = NSTemporaryDirectory() as NSString
    if directory != "" {
      let path = directory.appendingPathComponent(NSUUID().uuidString + ".mp4")
      return URL(fileURLWithPath: path)
    }
    return nil
  }
  
  
  func startRecording() {
    if movieOutput.isRecording == false {
      let connection = movieOutput.connection(with: AVMediaType.video)
      if (connection?.isVideoOrientationSupported)! {
        connection?.videoOrientation = currentVideoOrientation()
      }
      if (connection?.isVideoStabilizationSupported)! {
        connection?.preferredVideoStabilizationMode = AVCaptureVideoStabilizationMode.auto
      }
      
      let device = activeInput.device
      //    device.accessibilityFrame = CGRect(x: 0, y: 0, width: 100, height: 100)//not working to change vide dimensions
//      device.activeVideoMinFrameDuration = CMTime(value: 1, timescale: 5) //crash application
//      camera.activeVideoMaxFrameDuration = CMTime(value: 1, timescale: 5) //crash application
      if (device.isSmoothAutoFocusSupported) {
        do {
          try device.lockForConfiguration()
          device.isSmoothAutoFocusEnabled = false
          device.unlockForConfiguration()
        } catch {
          print("Error setting configuration: \(error)")
        }
      }
      outputURL = tempURL()
      movieOutput.startRecording(to: outputURL, recordingDelegate: self)
    }
    //    else {
    //      stopRecording()
    //    }
  }
  
  
  func stopRecording() {
    if movieOutput.isRecording == true {
      movieOutput.stopRecording()
    }
  }
  
  /*
  func getDevice(position: AVCaptureDevice.Position) -> AVCaptureDevice? {
    //    camera = getDevice(position: .front)! //not working activeVideoMinFrameDuration, activeVideoMaxFrameDuration got vide FPS for front camera
    //    camera = getDevice(position: .back)! //Working activeVideoMinFrameDuration, activeVideoMaxFrameDuration got vide FPS for back camera
    let devices: NSArray = AVCaptureDevice.devices() as NSArray;
     for de in devices {
        let deviceConverted = de as! AVCaptureDevice
        if(deviceConverted.position == position){
           return deviceConverted
        }
     }
     return nil
  }
  */
  
}




















extension VideoRecordVC: AVCaptureFileOutputRecordingDelegate {
  func fileOutput(_ output: AVCaptureFileOutput, didStartRecordingTo fileURL: URL, from connections: [AVCaptureConnection]) {
    print("didStartRecordingTo")
    UtilitySwift.showToast("Video recording started. Video length 13 sec")
//    for aVCaptureConnection in connections {
//      aVCaptureConnection.activeVideoStabilizationMode
//    }
  }
  
  
  func fileOutput(_ output: AVCaptureFileOutput, didFinishRecordingTo outputFileURL: URL, from connections: [AVCaptureConnection], error: Error?) {
//    print("outputFileURL = ")
//    print(outputFileURL)
//    print("error = ")
//    print(error)
    
    if (error != nil) {
//      UtilitySwift.showToast(STRING_DEFAULT_ERROR_MESSAGE)
      print("Error recording movie: \(error!.localizedDescription)")
    }
    else {
      UtilitySwift.showToast("Video captured")
      print(outputFileURL)
      let stringPDFInBase64 =  UtilitySwift.getBase64FromPDFVideoURL(outputFileURL)
      //        print(stringPDFInBase64)
      callback!([stringPDFInBase64,""]);
      /*
      //Hardcoded data start
      UtilitySwift.saveVideoInGalleryAfterDownloadFromURL(outputFileURL) { (isSavedFile) in
        print(isSavedFile)
      }
      //Hardcoded data end
      */
      self.buttonActionBack("")
    }
    
  }
  
}
