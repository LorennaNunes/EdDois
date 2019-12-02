package com.example.a18190_18343_projetoed2;

public class BucketHash {

    List<Cidade>[] data;

    public BucketHash()
    {
        data = new List[1000];
        for(int i = 0; i < 1000; i++)
            data[i] = new List<Cidade>();
    }

    public int Hash(String s)
    {
        long tot = 0;
        char[] charray;
        charray = s.toUpperCase().toCharArray();
        for (int i = 0; i <= s.length() - 1; i++)
            tot += 37 * tot + (int)charray[i];
        tot = tot % data.length - 1;
        if (tot < 0)
            tot += data.length - 1;
        return (int)tot;
    }

    public void Insert(Cidade item)
    {
        int hash_value = Hash(item.nomeCidade);
        if (!data[hash_value].exists(item))
            data[hash_value].insertAfterEnd(item);
    }

    public boolean Remove(Cidade item)
    {
        int hash_value = Hash(item.nomeCidade);
        if (data[hash_value].exists(item))
        {
            data[hash_value].remove(item);
            return true;
        }
        return false;
    }

}
