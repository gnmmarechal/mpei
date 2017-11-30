function C = ContarMembro(B, k, member)
  C = 0;
  for i = 1:k
    member = [member num2str(i)];
    val = rem(string2hash(member, "djb2"), length(B)) + 1;
    if B(val) > 0
      C = B(val);
      break
    end
  end
end