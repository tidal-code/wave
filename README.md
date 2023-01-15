# Tidal Wave

Wave browser automation library is a Java based Selenium framework to automate complex UIs. The most impressive feature of this library is that you can forget about switching iFrames and handling spinners. The framework will do it automatically for you. It has got implicit auto-wait of 5 seconds and a lot of easy to use features that makes your life as an automation engineer very easy.

## Downloads

You can download the dependency from maven

``` maven
<dependency>
   <groupId>io.github.tidal-code</groupId>
   <artifactId>wave</artifactId>
   <version>1.2.0</version>
</dependency>
```

## Usage Example:

``` java
Browser.open("https:\\google.co.nz");
find("name:q").sendKeys("hello");
find("name:q").shouldHave(exactText("hello"));
Browser.close();
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
