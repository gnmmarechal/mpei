%%False Positives

%Real Value

ninter = length(intersect(Members,Test));
nfpos = nsuc - ninter;

%nsuc is a number that was calculated before that would check
%the number of Test that were in Members using existMember
%Code:
nsuc=0;
  for i=1:length(Test)
    check = existMember;
    if check %=true
      nsuc+=1;
    end  
  end  


%Teo

%Probability of False Positive
pteo = (1-(1-1/m).^(k*nSM)).^k;

%k = Number of Hash Functions 
%m = BloomFilter Size
%nSM = Number of Members in BloomFilter