# Software assistent with Spring Rest and Selenium

This demo project shows how to remote control the a [web browser](https://www.mozilla.org/firefox/) with a [REST service](https://spring.io/guides/gs/rest-service/)
using [Selenium web browser automation](http://www.seleniumhq.org/).

## Usage

1. Download project
1. Start Docker
1. Start VNC viewer
1. Send HTTP GET requests to Spring REST

```bash
# download project
git clone https://github.com/phoen1x/software-assistent-spring-rest-selenium.git
cd software-assistent-spring-rest-selenium

# Start Docker in command line terminal
docker-compose up --build; docker-compose down

# Open your favorite VNC viewer
# https://www.realvnc.com/en/connect/download/viewer/
#     host: 127.0.0.1
#     port: 5900
#     password: secret
xdg-open 'vnc://:secret@127.0.0.1:5900'
# Now you should see a black screen with a Ubuntu logo
# more info https://github.com/SeleniumHQ/docker-selenium

# wait for Docker Terminal to show:  Started SeleniumApplication in ... seconds
# then chose your favorite way to send HTTP GET requests
firefox http://localhost:8080/api/selenium/webpage/crawl?html=true
google-chrome http://localhost:8080/api/selenium/webpage/crawl?html=true
curl -i http://localhost:8080/api/selenium/webpage/crawl

# get HTML content of current webpage
curl -i http://localhost:8080/api/selenium/session/html

# close web browser
firefox http://localhost:8080/api/selenium/session/close

# hit STRG+C in the Docker terminal stop the demo
```

## Beyond this Demo

Read [my article about this project](http://www.livingfire.de/skylar/software-assistent-spring-rest-selenium-en/) for more information like

* How the source code works
* Run services without Docker
* Remote control your local installed web browser
* Add sound to Docker
