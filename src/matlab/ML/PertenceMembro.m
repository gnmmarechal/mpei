function ok = PertenceMembro(B, k, member)
  ok = true;
  for i = 1:k
    member = [member num2str(i)];
    val = rem(string2hash(member, "djb2"), length(B)) + 1;
    if B(val) == 0 % Não precisa de alterações para o Counting Filter pois já verificava se não era 0
      ok = false;
      break
    end
  end
  
end