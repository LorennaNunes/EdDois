package com.example.a18190_18343_projetoed2;

public class BucketHash {
    List<String> data;
    public BucketHash()
    {
        data = new List<String>();
    }

    public int Hash(String s)
    {
        long tot = 0;
        char[] charray;
        charray = s.toUpperCase().toCharArray();
        for (int i = 0; i <= s.length() - 1; i++)
            tot += 37 * tot + (int)charray[i];
        tot = tot % data.qtd - 1;
        if (tot < 0)
            tot += data.qtd - 1;
        return (int)tot;
    }

    public void Insert(String item)
    {
        int hash_value = Hash(item);
        if (!data[hash_value].Contains(item))
            data[hash_value].Add(item);
    }
    public boolean Remove(String item)
    {
        int hash_value = Hash(item);
        if (data[hash_value].Contains(item))
        {
            data[hash_value].Remove(item);
            return true;
        }
        return false;
    }
}
