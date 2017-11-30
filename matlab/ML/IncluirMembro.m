function B = IncluirMembro(B, k, member)
  for i = 1:k
    member = [member num2str(i)]
    h = string2hash(member, "djb2")
    val = rem(h, length(B)) + 1;
    B(val) = 1;
    B
  end
end