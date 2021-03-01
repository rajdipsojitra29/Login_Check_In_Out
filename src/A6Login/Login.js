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










//Images
const logo1 = '../A2Assets/Images/logo3.png';

const statusBarHeight = getStatusBarHeight();
const screenWidth = Dimensions.get('window').width
const screenHeight = Dimensions.get('window').height

const api = create({
    baseURL: APIConstant.baseURL,
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/x-www-form-urlencoded',
    },
})












class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            navigation: null,
            dataInitial: '',
            dataFromPreviousClass: '',
            textInputEmployeeId: '', //HR-EMP-00037
            textInputPassword: '', //bitbuffs@123
        }
        this.state.navigation = this.props.navigation;
        this.state.dataInitial = this.props.route.params.dataInitial;
        // this.state.dataFromPreviousClass = this.props.route.params.dataForNextComponent;
    }

    componentDidMount() {
        this.navigationListenerWillFocusEveryTimeCallOnPageLoad()
        this.focusListener = this.props.navigation.addListener('focus', this.navigationListenerWillFocusEveryTimeCallOnPageLoad)
        HardcodedData.login(Login)
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

    isValidAllTextInput = () => {
        var isValidAllTextInputTemp = false
        if (this.state.textInputEmployeeId.length < 1) {
            // Toast.show('Please enter valid mobile or email',{position:Toast.positions.CENTER})
            isValidAllTextInputTemp = false
            Alert.alert(
                "",
                'Please enter employee id',
                [
                    {
                        text: 'OK',
                        onPress: () => {
                            console.log("OK Pressed")
                            // Toast.show('ok',{position:Toast.positions.CENTER})
                            this.refTextInputEmployeeId.focus()
                        }
                    }
                ],
                {
                    cancelable: true,
                    onDismiss: () => {
                        // Toast.show('onDismiss',{position:Toast.positions.CENTER})
                        this.refTextInputEmployeeId.focus()
                    }
                },
            );
        }
        else if (this.state.textInputPassword.length < 1) {
            // Toast.show('Please enter minimum 8 digit password',{position:Toast.positions.CENTER})
            isValidAllTextInputTemp = false
            Alert.alert(
                "",
                'Please enter password',
                [
                    {
                        text: 'OK',
                        onPress: () => {
                            console.log("OK Pressed")
                            // Toast.show('ok',{position:Toast.positions.CENTER})
                            this.refTextInputPassword.focus()
                        }
                    }
                ],
                {
                    cancelable: true,
                    onDismiss: () => {
                        // Toast.show('onDismiss',{position:Toast.positions.CENTER})
                        this.refTextInputPassword.focus()
                    }
                },
            );
        }
        else {
            isValidAllTextInputTemp = true
        }
        return isValidAllTextInputTemp
    }

    clearAllTextInput = () => {
        this.setState({
            textInputEmployeeId: '',
            textInputPassword: '',
        });
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

    onPressLogin = () => {
        // console.log('onPressLoginButton')
        // Toast.show('onPressLogin',{position:Toast.positions.CENTER})  

        if (this.isValidAllTextInput()) {
            this.getResponseFromAPI_MobileAuthenticationLogin(this.state.textInputEmployeeId, this.state.textInputPassword, (callbackData) => {
                console.log('callbackData = ' + callbackData)
                console.log('callbackData.access_token = ' + callbackData.access_token)

                var jSONData = Utility.getJSONFromObject(callbackData)
                // console.log('jSONData = ',jSONData)
                // console.log('jSONData employee_name = ',jSONData.employee_name)
                // console.log('jSONData access_token  = ',jSONData.access_token)

                KeychainManager.setData('access_token','Bearer ' + jSONData.access_token);

                this.clearAllTextInput()
                this.state.navigation.navigate('CheckInOut')    
            })
        }
    }

    getResponseFromAPI_MobileAuthenticationLogin = async (employeeIdObj, passwordObj, callback) => {
        // console.log('getResponseFromAPI_MobileAuthenticationLogin')

        this.showLoadingSpinnerOverlay()

        let grant_type = 'password'
        let client_id = '0412d50b81'
        let employee_id = employeeIdObj
        let password = passwordObj

        api.post(`one_fm.api.mobile.authentication.login?grant_type=${grant_type}&client_id=${client_id}&employee_id=${employee_id}&password=${password}`)
            .then(response => {
                // console.log('response = ', response)
                // console.log('response.ok = ', response.ok)
                // console.log('response.problem = ',response.problem)
                // console.log('response.originalError = ',response.originalError)
                // console.log('response.data = ', response.data)
                // console.log('response.status = ',response.status)

                // console.log('response.data.message = ', response.data.message)
                // console.log('response.data.message.error = ', response.data.message.error)

                if (response.data.message.error === undefined) {
                    // return response.data.message
                    Toast.show('Login Successful', { position: Toast.positions.CENTER })
                    return response.data.message
                }
                else {
                    Toast.show('Please enter valid Employee id OR Password', { position: Toast.positions.CENTER })
                    this.hideLoadingSpinnerOverlay()
                }
            })
            .then(responseDataMessage => {
                // console.log('responseDataMessage = ', responseDataMessage)
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

    onPressTest1 = () => {
        // console.log('onPressTest1')
        // Toast.show('onPressTest1',{position: Toast.positions.CENTER})
        
        // this.getResponseFromAPI_MobileFaceRecognitionEnroll("jsdfklajsdfk", (callbackData) => {
        //     console.log('getResponseFromAPI_MobileFaceRecognitionEnroll callbackData = ' + callbackData)
        // })

        this.state.navigation.navigate('CheckInOut')    

        // KeychainManager.setData('user_data',"shreeji data great");
        // KeychainManager.clearData('user_data');
    }

     async onPressTest2() {
        console.log('onPressTest2')
        // Toast.show('onPressTest2',{position: Toast.positions.CENTER})

        // this.state.navigation.navigate('CheckInOut')

        // let _data = await KeychainManager.getData('access_token');
        // Toast.show(_data,{position: Toast.positions.CENTER})
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

                            <Image style={styles.imageLogo} source={require(logo1)} />

                            <Fumi
                                // ref={'refTextInputEmployeeId'} //let refTextInputMobileorEmailTemp = this.refs["refTextInputEmployeeId"]; //refTextInputMobileorEmailTemp.clear(); //refTextInputMobileorEmailTemp.focus();
                                ref={element => this.refTextInputEmployeeId = element} //this.refTextInputEmployeeId.focus() //this.refTextInputEmployeeId.clear()
                                label={'Employee id'}
                                labelStyle={{ color: 'gray' }}
                                value={this.state.textInputEmployeeId}
                                // inputStyle={{fontSize: 30,fontWeight: 'bold',letterSpacing: 20}}
                                type={'text'}
                                iconClass={FontAwesomeIcon} //https://fontawesome.com/v4.7.0/icons/
                                iconName={'id-card-o'}
                                iconColor={'#000000'}
                                // placeholder={''}
                                iconSize={19}
                                // iconWidth={40}
                                // inputPadding={16}
                                style={styles.fumiMobileorEmployeeIdPassword}
                                secureTextEntry={false}
                                keyboardType="email-address"
                                onChangeText={(text) => {
                                    // console.log(text)
                                    text = text.replace(/ /g, '')
                                    // Toast.show(text,{position Toast.positions.CENTER})
                                    this.setState({ textInputEmployeeId: text })
                                }}
                            />

                            <Fumi
                                // ref={'refTextInputPassword'} //let refTextInputMobileorEmailTemp = this.refs["refTextInputEmployeeId"]; //refTextInputMobileorEmailTemp.clear(); //refTextInputMobileorEmailTemp.focus();
                                ref={element => this.refTextInputPassword = element}  //this.refTextInputEmployeeId.focus() //this.refTextInputEmployeeId.clear()
                                label={'Password'}
                                labelStyle={{ color: 'gray' }}
                                value={this.state.textInputPassword}
                                // inputStyle={{fontSize:30,fontWeight:'bold',letterSpacing:20}}
                                iconClass={FontAwesomeIcon} //https://fontawesome.com/v4.7.0/icons/
                                iconName={'key'}
                                iconColor={'#000000'}
                                // iconSize={20}
                                // iconWidth={40}
                                // inputPadding={16}
                                style={styles.fumiMobileorEmployeeIdPassword}
                                secureTextEntry={true}
                                keyboardType="default"
                                onChangeText={(text) => {
                                    // console.log(text)
                                    text = text.replace(/ /g, '')
                                    this.setState({ textInputPassword: text })
                                }}
                            />

                            <TouchableOpacity
                                style={styles.touchableOpacityLogin}
                                onPress={() => {
                                    this.onPressLogin()
                                }}>
                                <Text style={styles.textLogin}>Login</Text>
                            </TouchableOpacity>

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
        justifyContent: 'center',
    },
    imageLogo: {
        // width:10,
        height: 100,
        aspectRatio: 2 / 1,
        borderRadius: 10,
        resizeMode: 'contain', //'cover' | 'contain' | 'stretch' | 'repeat' | 'center'; 
        // marginTop:100,
        marginBottom: 50
    },
    fumiMobileorEmployeeIdPassword: {
        width: '90%',
        backgroundColor: Constants.Color.fumi, //lightgray, #00000020
        borderRadius: 5,
        marginTop: 20,
    },
    touchableOpacityLogin: {
        width: '50%',
        height: 40,
        borderRadius: 5,
        backgroundColor: '#ff7f50',
        alignItems: 'center',
        justifyContent: 'center',
        marginTop: 30,

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

export default (Login);