import { getStatusBarHeight } from 'react-native-status-bar-height'
import React, { Component, useState } from 'react'
import {
    NativeModules,
    StyleSheet,
    ScrollView,
    Dimensions,
    View,
    Text,
    // Button, 
    Image,
    TouchableOpacity,
    Alert,
    Platform,
} from 'react-native'
import FontAwesomeIcon from 'react-native-vector-icons/FontAwesome'
import { Fumi } from 'react-native-textinput-effects'
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view'
import Toast from 'react-native-root-toast'
import Spinner from 'react-native-loading-spinner-overlay'
import Utility from '../A3Utility/Utility'
import HardcodedData from '../A3Utility/HardcodedData'
import Constants from '../A3Utility/Constants'
import APIConstant from "../A4APIManager/APIConstant";
import { create } from 'apisauce'
import KeychainManager from '../A3Utility/KeychainManager'











const statusBarHeight = getStatusBarHeight();
const screenWidth = Dimensions.get('window').width
const screenHeight = Dimensions.get('window').height

var javaFunctionsToCallInReactNativeModule = NativeModules.JavaFunctionsToCallInReactNativeModule;
var swiftFunctionsToCallInReactNativeModule = NativeModules.SwiftFunctionsToCallInReactNativeModule;

const api = create({
    baseURL: APIConstant.baseURL,
    // headers: {
    //     // 'Accept': 'application/json',
    //     'Content-Type': 'multipart/form-data',
    //     'Authorization': 'Bearer oZCdtWmY0WCIpv8HtZbjvOhq4ZQs1z',
    // },
})











class CheckInOut extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            navigation: null,
            dataInitial: '',
            dataFromPreviousClass: '',
            buttonTitleEnrollLogout: 'Enroll',
            access_token: '',
        }
        this.state.navigation = this.props.navigation;
        this.state.dataInitial = this.props.route.params.dataInitial;
        // this.state.dataFromPreviousClass = this.props.route.params.dataForNextComponent;
    }

    componentDidMount() {
        this.navigationListenerWillFocusEveryTimeCallOnPageLoad()
        this.focusListener = this.props.navigation.addListener('focus', this.navigationListenerWillFocusEveryTimeCallOnPageLoad)
        HardcodedData.checkInOut(CheckInOut)
    }

    UNSAFE_componentWillMount() {
    }

    shouldComponentUpdate() {
        // console.log('3shouldComponentUpdate');
        // Toast.show('shouldComponentUpdate',{position:Toast.positions.CENTER})
        return true;
    }

    componentWillUnmount() {
        // console.log('2componentWillUnmount');
        // Toast.show('componentWillUnmount',{position:Toast.positions.CENTER})
        this.focusListener.remove;
    }

    componentDidCatch(error, info) {
        // console.log('componentDidCatch');
        // console.log(error);
        // console.log(info);
    }

    navigationListenerWillFocusEveryTimeCallOnPageLoad = () => {
        // console.log('navigationListenerWillFocusEveryTimeCallOnPageLoad')
    }

    showLoadingSpinnerOverlay = () => {
        this.setState({
            spinner: true
        });
    }

    hideLoadingSpinnerOverlay = () => {
        this.setState({
            spinner: false
        });
    }

    onPressBack = () => {
        this.state.navigation.pop()
        // this.state.navigation.popToTop()
    }

    onPressEnroll = () => {
        this.getAndSetAccessToken()
        var self = this
        if (this.state.buttonTitleEnrollLogout == "Enroll") {
            setTimeout(function () {
                // console.log('onPressEnroll')
                // Toast.show('onPressEnroll',{position:Toast.positions.CENTER})  
                if (Platform.OS === 'android') {
                    if (javaFunctionsToCallInReactNativeModule) {
                        if (javaFunctionsToCallInReactNativeModule.onClickButtonEnroll !== undefined) {
                            javaFunctionsToCallInReactNativeModule.onClickButtonEnroll('', (callbackSuccess1, callbackSuccess2) => {
                                // console.log(callbackSuccess1)
                                // console.log(callbackSuccess2)
                                // Toast.show(callbackSuccess1, { position: Toast.positions.CENTER })
        
                                self.getResponseFromAPI_MobileFaceRecognitionEnroll(callbackSuccess1, (callbackData) => {
                                    // console.log('callbackData = ' + callbackData)
                                    self.setState({
                                        buttonTitleEnrollLogout: 'Logout'
                                    })
                                })
    
                            }, (callbackError1) => {
                                // console.log(callbackError1)
                            });
                        }
                    }
                }
                else if (Platform.OS === 'ios') {
                    if (swiftFunctionsToCallInReactNativeModule) {
                        if (swiftFunctionsToCallInReactNativeModule.buttonActionEnroll !== undefined) {
                            swiftFunctionsToCallInReactNativeModule.buttonActionEnroll('', (callbackSuccess1, callbackSuccess2) => {
                                // console.log(callbackSuccess1)
                                // console.log(callbackSuccess2)
                                // Toast.show(callbackSuccess1, { position: Toast.positions.CENTER })

                                self.getResponseFromAPI_MobileFaceRecognitionEnroll(callbackSuccess1, (callbackData) => {
                                    // console.log('callbackData = ' + callbackData)
                                    self.setState({
                                        buttonTitleEnrollLogout: 'Logout'
                                    })
                                })
                                
                            }, (callbackError1) => {
                                // console.log(callbackError1)
                            });
                        }
                    }
                }
            }, 1000);
        }
        else {
            Alert.alert(
                '',
                'Are you sure want to logout?',
                [
                    {
                        text: 'OK',
                        onPress: () => {
                            KeychainManager.clearData('access_token');
                            this.state.navigation.pop()
                            // this.state.navigation.popToTop()
                        }
                    },
                    {
                        text: 'Cancel',
                        onPress: () => {
                        }
                    }
                ],
                {
                    cancelable: true,
                    onDismiss: () => {
                        // Toast.show('onDismiss',{position:Toast.positions.CENTER})
                    }
                },
            );
        }
    }

    getResponseFromAPI_MobileFaceRecognitionEnroll = async (stringBase64, callback) => {
        // console.log('getResponseFromAPI_MobileFaceRecognitionEnroll')
        this.showLoadingSpinnerOverlay()

        const formData = new FormData(); 
        formData.append('video', stringBase64)

        api.post(`one_fm.api.mobile.face_recognition.enroll`,formData,{ headers: { 'Content-Type': 'multipart/form-data',
        'Authorization': this.state.access_token } })
            .then(response => {
                // console.log('response = ', response)
                // console.log('response.ok = ', response.ok)
                // console.log('response.problem = ', response.problem)
                // console.log('response.originalError = ', response.originalError)
                // console.log('response.data = ', response.data)
                // console.log('response.status = ', response.status)

                // console.log('response.data.message = ', response.data.message)
                // console.log('response.data.message.error = ', response.data.message.error)

                if (response.ok === true && response.status === 200) {
                    Toast.show(response.data.message, { position: Toast.positions.CENTER })
                    return response.data.message
                }
                else {
                    Toast.show(Constants.STRING_DEFAULT_ERROR_MESSAGE, { position: Toast.positions.CENTER })
                    this.hideLoadingSpinnerOverlay()
                }
            })
            .then(responseDataMessage => {
                console.log('responseDataMessage = ', responseDataMessage)
                if (responseDataMessage !== undefined) {
                    callback(responseDataMessage)
                }
                this.hideLoadingSpinnerOverlay()
            })
            .catch(error => {
                console.log('error = ', error)
                // Toast.show(Constants.STRING_DEFAULT_ERROR_MESSAGE,{position: Toast.positions.CENTER})
                this.hideLoadingSpinnerOverlay()
                reject(error)
            })
    };


    async getAndSetAccessToken() {
        let accessToken = await KeychainManager.getData('access_token');
        // Toast.show(accessToken,{position: Toast.positions.CENTER})
        this.setState({
            access_token: accessToken
        })
    }

    onPressTest1 = () => {
        // console.log('onPressTest1')
        // Toast.show('onPressTest1', { position: Toast.positions.CENTER })
        // KeychainManager.setData('access_token','shreeji maharaj');

        Toast.show(this.state.access_token, { position: Toast.positions.CENTER })
    }

    async onPressTest2() {
        // console.log('onPressTest2')
        // Toast.show('onPressTest2',{position: Toast.positions.CENTER})

        // let _data = await KeychainManager.getData('access_token');
        // // Toast.show(_data,{position: Toast.positions.CENTER})

        // this.setState({
        //     access_token: _data
        // })
    }
    









    addLoadingSpinnerOverlay = () => {
        return (
            <Spinner
                visible={this.state.spinner}
                // textContent={'Loading...'}
                // textStyle={styles.spinnerTextStyle}
                // color={'orange'}
                overlayColor={'#00000080'}
            />
        )
    }

    render() {
        return (
            <ScrollView style={styles.scrollViewMain}
                bounces={false}
                showsVerticalScrollIndicator={false}>
                <KeyboardAwareScrollView
                    bounces={false}
                    showsVerticalScrollIndicator={false}>
                    <View style={styles.viewMainFirst}>
                        <View style={styles.viewMainSecond}>
                            {this.addLoadingSpinnerOverlay()}

                            {/* <TouchableOpacity
                                style={styles.touchableOpacityLogin}
                                onPress={() => {
                                    this.onPressTest1()
                                }}>
                                <Text style={styles.textLogin}>Test1</Text>
                            </TouchableOpacity> */}

                            {/* <TouchableOpacity
                                style={styles.touchableOpacityLogin}
                                onPress={() => {
                                    this.onPressTest2()
                                }}>
                                <Text style={styles.textLogin}>Test2</Text>
                            </TouchableOpacity> */}

                            <TouchableOpacity
                                style={styles.touchableOpacityLogin}
                                onPress={() => {
                                    this.onPressEnroll()
                                }}>
                                <Text style={styles.textLogin}>{this.state.buttonTitleEnrollLogout}</Text>
                            </TouchableOpacity>

                        </View>
                    </View>
                </KeyboardAwareScrollView>
            </ScrollView>
        )
    }
}




















const styles = StyleSheet.create({
    scrollViewMain: {
        // flex:1,
        backgroundColor: '#ffffff',
    },
    viewMainFirst: {
        marginTop: statusBarHeight,
        // flex:1,
        backgroundColor: '#ffffff',
        alignItems: 'center',
        // justifyContent: 'center',
    },
    viewMainSecond: {
        width: '100%',
        // height:screenHeight-statusBarHeight-Utility.getScreenHeightInPersantage(12.1),
        height: screenHeight - statusBarHeight,
        // backgroundColor:'orange',
        alignItems: 'center',
        justifyContent: 'flex-end',
    },
    touchableOpacityLogin: {
        width: '50%',
        height: 40,
        borderRadius: 5,
        backgroundColor: '#ff7f50',
        alignItems: 'center',
        justifyContent: 'center',
        // marginTop: 30,
        marginBottom: 100,

        shadowColor: '#000000',
        shadowOffset: {
            width: 2,
            height: 2
        },
        shadowRadius: 5,
        shadowOpacity: 0.5,
        elevation: 2,
    },
    textLogin: {
        color: 'white',
        fontWeight: 'bold'
    },
});

export default (CheckInOut);