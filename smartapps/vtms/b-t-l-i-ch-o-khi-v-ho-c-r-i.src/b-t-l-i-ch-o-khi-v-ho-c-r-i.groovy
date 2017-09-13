definition(
    name: "Bật lời chào khi về hoặc rời",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Bạn muốn hệ thống thực hiện lời chào",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")


preferences 
{
    section("Kích hoạt kịch bản")
    {
        input name:"sel",type:"enum", title:"Hoạt động", options: ["on","off"], defaultValue:"off"
    }

    section("Cảm biến hiện diện")
    {
        input("presence","capability.presenceSensor",title:"Cảm biến hiện diện")
    }  
}
def installed() 
{
	init()
}

def updated() 
{
	init()	
}

def init()
{
    subscribe(presence,"presence",presence_1)
}

def presence_1(evt)
{
	if (sel=="on")
	{
        if(evt.value=="present")
        {
            sendPush("Chào bạn đã về Nhà! ")
        }

        if(evt.value=="not present")
        {
            sendPush("Bạn đã rời khỏi Nhà!")
        }
   } 
}

