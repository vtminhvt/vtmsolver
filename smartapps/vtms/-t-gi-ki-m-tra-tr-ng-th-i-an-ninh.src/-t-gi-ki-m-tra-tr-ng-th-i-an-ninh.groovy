definition(
    name: "Đặt giờ kiểm tra trạng thái an ninh",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Đặt thời gian để hệ thống kiểm tra an ninh trước khi đi ngủ",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")
//test: OK

preferences 
{
	section("Kích hoạt kịch bản")
	{
		input name:"sel",type:"enum", title:"Hoạt động", options: ["on","off"], defaultValue:"off"
	}
    
    section("Đặt thời gian cảnh báo An toàn")
    {
     input name: "timeCB", type: "time", title: "Thời gian cảnh báo an toàn"
    }
   
	
    
    section("Chọn các cảm biến đóng mở cần cảnh báo")
    {
    	input("cs1","capability.contactSensor",title:"Cảm biến cửa cuốn 1")
    	input("cs2","capability.contactSensor",title:"Cảm biến cửa cuốn 2")
    }
    
}
def init()
{
    subscribe(cs1,"contact",cs_1)
    subscribe(cs2,"contact",cs_2)
}

def installed() 
{
    init()
	schedule(timeCB, cb)
}

def updated() 
{
	init()	
	unschedule()
	schedule(timeCB, cb)
}

def initialize() 
{
    schedule(timeCB, cb)   
}
   
def cb()
{
    if (sel=="on")
    {
        def v1=(cs1.currentValue("contact")=="closed")
        def v2=(cs2.currentValue("contact")=="closed")
        
        
        def t1=cs1.currentValue("contact")
        def t2=cs2.currentValue("contact")
        if (t1=="open") t1="Mở" else t1="Đóng"
        if (t2=="open") t2="Mở" else t2="Đóng"
        
        if (! (v1 && v2))
        {
           sendPush("[An ninh]:Vẫn còn cửa cuốn chưa đóng. Trạng thái các cửa là: \n Cửa cuốn1: ${t1} \n Cửa cuốn2: ${t2}")
        }
        else
        {
            sendPush("[An ninh]:Cửa cuốn Nhà của bạn đã được đóng an toàn!")
        }
    }
}
