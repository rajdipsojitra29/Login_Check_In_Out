/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import {
    SafeAreaView,
    StyleSheet,
    ScrollView,
    View,
    Text,
    StatusBar,
} from 'react-native';

import {
    LearnMoreLinks,
    Colors,
    DebugInstructions,
    ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

import 'react-native-gesture-handler'
import Toast from 'react-native-root-toast'
import { NavigationContainer } from '@react-navigation/native'
import { createStackNavigator, Header, StackHeaderProps, } from '@react-navigation/stack'
import { RootSiblingParent } from 'react-native-root-siblings'
import AfterAppLaunchFirstInitilization from './A3Utility/AfterAppLaunchFirstInitilization'
import Login from './A6Login/Login'
import CheckInOut from './A7CheckInOut/CheckInOut'
import Utility from './A3Utility/Utility'
import HardcodedData from './A3Utility/HardcodedData'











const Stack = createStackNavigator();











// const App: () => React$Node = () => {
function App() {

    function afterAppLaunchFirstInitilization() {
        // console.log('afterAppLaunchFirstInitilization')
        // Toast.show('afterAppLaunchFirstInitilization',{position:Toast.positions.CENTER})

        AfterAppLaunchFirstInitilization.initilization()

        HardcodedData.app()
    }











    return (
        <RootSiblingParent>
            <NavigationContainer>
                <StatusBar style="auto" />
                {afterAppLaunchFirstInitilization()}
                <Stack.Navigator>

                    <Stack.Screen
                        name="Login"
                        component={Login}
                        options={{
                            title: 'Login',
                            headerShown:false,
                            // headerLeft:null,
                            headerBackTitleVisible: false,
                            headerBackImage: ({ tintColor }) => (
                                <MaterialCommunityIcons
                                    name="arrow-left-circle-outline"
                                    color={'red'}
                                    size={24}
                                    style={{ marginHorizontal: Platform.OS === 'ios' ? 8 : 0 }}
                                />
                            ),
                        }}
                        initialParams={{ dataInitial: 'test data' }}
                    />

                    <Stack.Screen
                        name="CheckInOut"
                        component={CheckInOut}
                        options={{
                            title: 'Check In Out',
                            headerShown: true,
                            headerLeft:null,
                        }}
                        initialParams={{ dataInitial: 'test data' }}
                    />

                </Stack.Navigator>
            </NavigationContainer>
        </RootSiblingParent>
    );
};











const styles = StyleSheet.create({
    scrollView: {
        backgroundColor: Colors.lighter,
    },
});

export default App;
