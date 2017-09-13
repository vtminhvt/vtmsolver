definition(
    name: "[Nhà]Mở cửa thì báo động hai bước V2",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Đặt giờ mà hệ thống kiểm tra mức độ an toàn",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")

preferences 
{
    section("Kích hoạt kịch bản")
    {
        input name:"sel",type:"enum", title:"Hoạt động", options: ["on","off"], defaultValue:"on"
    }
    
    section("Chọn khoảng thời gian")
    {
         input name: "timeB", type: "time", title: "Đặt thời gian bắt đầu"
         input name: "timeE", type: "time", title: "Đặt thời gian kết thúc"
    }
   section("Chọn Báo động")
    {
        input("alamH","capability.alarm",title:"Chọn thiết bị báo động")
    } 
    
   section ("Thời gian báo động lần 1")
    {
         input name: "tp1", type: "number", title: "Báo động trong bao lâu(giây)?", defaultValue:"20"
    }
    
    section("Kiểu báo động lần 1")
    {
        input name:"typ1",type:"enum", title:"Chọn kiểu báo động", options: ["A","L"], defaultValue:"L"
    }
    
    section ("Bao nhiêu giây thì kiểm tra lần 2, kể từ khi kết thúc lần 1")
    {
         input name: "tp", type: "number", title: "Thời gian(giây)?", defaultValue:"2"
    }
   
    section ("Thời gian báo động lần 2")
    {
         input name: "tp2", type: "number", title: "Báo động trong bao lâu(giây)?", defaultValue:"20"
    }
    section("Kiểu báo động lần 2")
    {
        input name:"typ2",type:"enum", title:"Chọn kiểu báo động", options: ["A","L"], defaultValue:"L"
    }

    section("Chọn cảm biến khi phát hiện mở cửa")
    {
        input("cs1","capability.contactSensor",title:"Cảm biến đóng, mở")
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
    subscribe(cs1,"contact",cs_1)
    subscribe(alamH,"alarm",alam_H)
}

def cs_1(evt)
{
    def timeofB = timeToday(timeB)
    def timeofE= timeToday(timeE)
    def timeC=now()
    
    def p1= tp1*1000
	def p=	tp*1000
	
	
    def dk1= (timeofB<timeofE) && (timeC >= timeofB.time && timeC<=timeofE.time)
    def dk2= (timeofB>timeofE) && (timeC >= timeofB.time || timeC<=timeofE.time)
    
    if (evt.value == "open" && sel == "on")
    {
        if (dk1 || dk2)
        {
        	     sendPush("Báo động bước 1, Kiểu báo động ${typ1}, Lý do:${evt.displayName} phát hiện bị mở.")
           
           if(typ1=="L") 
            {
           		alamH.strobe()
        		schedule(now()+p1,alamF) // turn off in 10 second
                schedule(now()+p1+p,laplai)
        	}
            if(typ1=="A")
            {	
        		alamH.siren()
        		schedule(now()+p1,alamF) // turn off in 10 second
                schedule(now()+p+p1,laplai)
        	}
        	if(typ1=="AL")
        	{	
        		alamH.both()
           		schedule(now()+p1,alamF) // turn off in 10 second
                schedule(now()+p+p1,laplai)
        	}
		}
	    }
 }
 
def laplai()
{
	def timeofB = timeToday(timeB)
    def timeofE= timeToday(timeE)
    def timeC=now()
    def p2= tp2*1000
    def t_c = cs1.currentValue("contact")
    
	if (t_c == "open")
	{	
    		
        	if(typ2=="L") 
            {
           		alamH.strobe()
        		schedule(now()+p2,alamF) // turn off in 10 second
        	}
            if(typ2=="A")
            {
        		alamH.siren()
        		schedule(now()+p2,alamF) // turn off in 10 second
    
        	}
        	if(typ2=="AL")
        	{	
        		alamH.both()
           		schedule(now()+p2,alamF) // turn off in 10 second
    
        	}
	}
}
def alamF()
{
	alamH.off()
    
}