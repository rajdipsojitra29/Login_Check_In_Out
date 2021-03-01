//Utility.js
import {
    Dimensions,
} from 'react-native';
import Toast from 'react-native-root-toast'










class Utility {
    static isStringLength(intLength, string) {
        // Toast.show(string,{position:Toast.positions.CENTER})
        if (string.length === intLength) {
            return true;
        }
        return false;
    }

    static getScreenWidthInPersantage(persantage) {
        // screenHeight = Dimensions.get('window').height
        // return screenHeight
        return (Dimensions.get('window').width/(100/persantage))
    }

    static getScreenHeightInPersantage(persantage) {
        // screenHeight = Dimensions.get('window').height
        // return screenHeight
        return (Dimensions.get('window').height/(100/persantage))
    }

    static getJSONFromObject(object) {
        var jSONString = JSON.stringify(object); 
        var jSONTemp = JSON.parse(jSONString);
        return jSONTemp
    }
}


export default(Utility);