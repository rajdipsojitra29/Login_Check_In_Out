//KeychainManager.js
import {
	Dimensions,
	Platform
} from 'react-native';
import Toast from 'react-native-root-toast'
import * as Keychain from 'react-native-keychain';










class KeychainManager {
	
	static async setData(stringKey, stringValue) {
        // Toast.show(stringKey + ' ' + stringValue, { position: Toast.positions.CENTER })
		try {
			await Keychain.setInternetCredentials(
				stringKey,
				stringKey,
				stringValue
				)
		} catch (err) {
            Toast.show('Could not save data for ' + stringKey, { position: Toast.positions.CENTER })
		}
	}

	static async getData(stringKey) {
        // Toast.show(stringKey, { position: Toast.positions.CENTER })
		try {
			const credentials = await Keychain.getInternetCredentials(stringKey);
			
			if (credentials) {
				// console.log(credentials)
				// console.log('username',credentials.username)
				// console.log('password',credentials.password)
				// Toast.showWithGravity(credentials.password, Toast.SHORT, Toast.CENTER);
				return credentials.password
			} 
			else {
				// Toast.showWithGravity('No data stored.', Toast.SHORT, Toast.CENTER);
				return ''
			}
		} catch (err) {
			// Toast.showWithGravity(err, Toast.SHORT, Toast.CENTER);
			return ''
		}
	}

	static clearData(stringKey) {
		// Toast.showWithGravity(stringKey, Toast.SHORT, Toast.CENTER);
		Keychain.resetInternetCredentials(stringKey)   
	}

}


export default(KeychainManager);