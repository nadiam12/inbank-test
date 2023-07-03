## InBank Loan Calculator

### Project description 

It is an engine which takes in personal code, loan amount, loan period in
months and returns a decision (negative or positive), the amount and period.


### How to run 

* Make sure you have **_Java v11.0.19_** or higher, **_nmp v9.6.7_** or higher, **_node.js v.20.3.1_** or higher are installed.

Open terminal from the base directory of inbank application
```
> cd frontend 
> npm run build
```
All static files that are necessary for a nice view will be build and copied to *backend/src/main/resources/static* folder.  
There is no need to launch frontend server.

```
> cd ../backend 
> ./gradlew bootRun
```
The server is up and running. Now you can open page in browser. 
http://localhost:8080


