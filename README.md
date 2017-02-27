[![NPM](https://nodei.co/npm/cordova-plugin-phonecallstate.png?stars&downloads)](https://nodei.co/npm/cordova-plugin-phonecallstate/)


# cordova-plugin-phonecallstate

Phone Call State Plugin for Cordova Android, compatible for ionic 2.


## Installation
```
cordova plugin add cordova-plugin-phonecallstate
```

## Usage 
```
declare var PhoneCallState: any;

PhoneCallState.watchState((response) => {
  let state: string = response.state;
  let incoming_number: string = response.number;

  switch (state) {
    case 'IDLE':
      break;
    case 'OFFHOOK':
      break;
    case 'RINGING':
      break;
  }
}, (err) => {
  // Permission Denied
});
```
 