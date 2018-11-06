definition(
    name: "Đặt lời chào buổi sáng",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Thực hiện một lời chào khi bình minh tỏa nắng",
    category: "Safety & Security",
    iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")

preferences 
{
 section("Kích hoạt kịch bản")
    {
        input name:"sel",type:"enum", title:"Hoạt động", options: ["on","off"], defaultValue:"off"
    } 
}

def installed() 
{
    //schedule(timeCB, cb)
    subscribe(location, "sunset", sunsetHandler)
    subscribe(location, "sunrise", sunriseHandler)
}

def updated() 
{
	unschedule()
	//schedule(timeCB, cb)
}

def sunsetHandler(evt)
{
if (sel=="on")
	{
		sendPush("Hoàng hôn:Lúc này mặt trời lặn.")
	}	
}

def sunriseHandler(evt)
{
if (sel=="on")
	{
		sendPush("Bình minh:Lúc này mặt trời mọc.")
    	sendPush("Chào buổi sáng, Ngày mới tốt lành.")
	}

}