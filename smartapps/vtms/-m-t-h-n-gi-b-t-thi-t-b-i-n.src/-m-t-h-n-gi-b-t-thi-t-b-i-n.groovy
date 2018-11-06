definition(
        name: "|Một|Hẹn giờ bật thiết bị điện",
        namespace: "VTMS",
        author: "Võ Thanh Minh",
        description: "Kịch bản điều khiển thiết bị dựa vào thói quen, sở thích và mệnh lệnh",
        category: "Safety & Security",
       iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")

preferences 
{
    section("Kích hoạt kịch bản")
    {
        input name:"sel",type:"enum", title:"Chọn ON để hoạt động, OFF để vô hiệu hóa", options: ["on","off"], defaulValue:"off"
    }
    section("Giờ hẹn")
    {
        input name: "timeB", type: "time", title: "Đặt thời gian bật thiết bị"
        input name: "timeE", type: "time", title: "Đặt thời gian tắt thiết bị"
    }
    section("Chọn công tắc, thiết bị điều khiển")
    {
        input("sw1","capability.switch",title:"Tên công tắc, thiết bị")
    }
}
def installed() 
{
	init()
	schedule(timeB, lightON)
	schedule(timeE, lightOFF)
}

def updated() 
{
	init()
    unschedule()
	schedule(timeB, lightON)
	schedule(timeE, lightOFF)
   	
}
def init()
{
  	subscribe(sw1,"switch",sw_1) 
}
def lightON()
{
	if (sel=="on") {
    sw1.on()
    }
}

def lightOFF()
{
	if (sel=="on")	{
    sw1.off()
    }
}