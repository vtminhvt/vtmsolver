definition(
    name: "[Gara]Gặp vật cản thì điều khiển cửa cuốn dừng",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Điều khiển",
    category: "Safety & Security",
    iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")
preferences 
{
 section("Chọn")
    {
    	input("sw1","capability.switch",title:"Khóa đ.khiển")             
       	input("sw2","capability.switch",title:"Chọn nút dừng")             
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
 subscribe(sw1,"switch",sw_1)
 subscribe(sw2,"switch",sw_2)  
}


def sw_1(evt)
{
if (evt.value == "on")
	{
		sw2.on()
	}

}