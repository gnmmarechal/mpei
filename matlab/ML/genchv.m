function r = genchv(dic, len, px)
  r = "";
  for k=1:len    
    nletras = find(px>rand()) ;
    in = nletras(1); 
    r =  strcat(r,dic(in));   
  end  
end