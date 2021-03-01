import UIKit
import AVFoundation

extension AVCaptureDevice {

    /// http://stackoverflow.com/questions/21612191/set-a-custom-avframeraterange-for-an-avcapturesession#27566730
    func configureDesiredFrameRate(desiredFrameRateMin: Int, desiredFrameRateMax: Int) {

        var isFPSSupported = false

        do {

            if let videoSupportedFrameRateRanges = activeFormat.videoSupportedFrameRateRanges as? [AVFrameRateRange] {
                for range in videoSupportedFrameRateRanges {
                    if (range.maxFrameRate >= Double(desiredFrameRateMax) && range.minFrameRate <= Double(desiredFrameRateMin)) {
                        isFPSSupported = true
                        break
                    }
                }
            }

            if isFPSSupported {
                try lockForConfiguration()
              activeVideoMaxFrameDuration = CMTimeMake(value: 1, timescale: Int32(desiredFrameRateMax))
              activeVideoMinFrameDuration = CMTimeMake(value: 1, timescale: Int32(desiredFrameRateMin))
                unlockForConfiguration()
            }

        } catch {
            print("lockForConfiguration error: \(error.localizedDescription)")
        }
    }

}
