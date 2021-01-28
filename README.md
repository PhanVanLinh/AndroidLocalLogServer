# AndroidLocalLogServer

Server app for client app at
https://github.com/PhanVanLinh/AndroidLocalLogClientTest

Communicate to client by IPC (Inter Process Communication) via AIDL (Android Interface Define Language)
Security by the same signature permission `android:protectionLevel="signature"`

## Feature
- Receive normal log / error log (`object Parcelable`) to server
- Display log of each application

## Next feature
- Delete log
- Search log
