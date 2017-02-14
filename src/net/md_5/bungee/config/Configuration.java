package net.md_5.bungee.config;

import com.google.common.collect.Sets;

import java.util.*;

/**
 *
 * Copyright (c) 2012, md_5. All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * The name of the author may not be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * You may not use the software for commercial software hosting services without
 * written permission from the author.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * @author md_5
 */
public class Configuration {

    private static final char       SEPARATOR = '.';

    public Map<String, Object>      self ;
    private Configuration           defaults;

    public Configuration(Configuration def) {
        this(new LinkedHashMap<String, Object>(), def);
    }

    Configuration(Map<?, ?> map, Configuration def) {
        self       = new LinkedHashMap<>();
        defaults   = def;

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String key = (entry.getKey() == null) ? "null" : entry.getKey().toString();

            if (entry.getValue() instanceof Map) {
                self.put(key, new Configuration((Map) entry.getValue(), (defaults == null) ? null : defaults.getSection(key)));
            } else {
                self.put(key, entry.getValue());
            }
        }
    }

    private Configuration getSectionFor(String path) {
        int index = path.indexOf(SEPARATOR);

        if (index == -1) return this;

        String root     = path.substring(0, index);
        Object section  = self.get(root);

        if (section == null) {
            section = new Configuration((defaults == null) ? null : defaults.getSection(path));
            self.put(root, section);
        }
        return (Configuration) section;
    }

    private String getChild(String path) {
        int index = path.indexOf(SEPARATOR);
        return (index == -1) ? path : path.substring(index + 1);
    }


    /*-----------------------------------------------------------------------------------------------------------------*/


    public Object get(String path) {
        return get(path, getDefault(path));
    }

    public <T> T get(String path, T def) {
        Configuration section = getSectionFor(path);
        Object          o;

        if (section == this) {
            o = self.get(path);
        } else {
            o = section.get(getChild(path), def);
        }

        if (o == null && def instanceof Configuration) {
            self.put(path, def);
        }
        return (o != null) ? (T) o : def;
    }

    public void set(String path, Object o) {
        if (o instanceof Map) {
            o = new Configuration((Map) o, (defaults == null) ? null : defaults.getSection(path));
        }

        Configuration section = getSectionFor(path);
        if (section == this) {
            if (o == null) {
                self.remove(path);
            } else {
                self.put(path, o);
            }
        } else {
            section.set(getChild(path), o);
        }
    }

    public Object getDefault(String path) {
        return (defaults == null) ? null : defaults.get(path);
    }

    public boolean contains(String path) {
        return get(path, null) != null;
    }


     /*-----------------------------------------------------------------------------------------------------------------*/



     public Configuration getSection(String path) {
         Object def = getDefault(path);
         return (Configuration) get(path, (def instanceof Configuration) ? def : new Configuration((defaults == null) ? null : defaults.getSection(path)));
     }

     public Collection<String> getKeys() {
         return Sets.newLinkedHashSet(self.keySet());
     }


    /*-----------------------------------------------------------------------------------------------------------------*/



    public Byte getByte(String path) {
        Object o = getDefault(path);
        return getByte(path, (o instanceof Number) ? ((Number) o).byteValue() : 0);
    }

    public Byte getByte(String path, Byte def) {
        Object o = get(path, def);
        return (o instanceof Number) ? ((Number) o).byteValue() : def;
    }

    public List<Byte> getByteList(String path) {
        List<?>     list    = getList(path);
        List<Byte>  result  = new ArrayList<>();

        for (Object o : list) {
            if (o instanceof Number) {
                result.add(((Number) o).byteValue());
            }
        }
        return result;
    }



    public Short getShort(String path) {
        Object o = getDefault(path);
        return getShort(path, (o instanceof Number) ? ((Number) o).shortValue() : 0);
    }

    public Short getShort(String path, short def) {
        Object o = get(path, def);
        return (o instanceof Number) ? ((Number) o).shortValue() : def;
    }

    public List<Short> getShortList(String path) {
        List<?>     list    = getList(path);
        List<Short> result  = new ArrayList<>();

        for (Object o : list) {
            if (o instanceof Number) {
                result.add(((Number) o).shortValue());
            }
        }
        return result;
    }



    public Integer getInteger(String path) {
        Object o = getDefault(path);
        return getInteger(path, (o instanceof Number) ? ((Number) o).intValue() : 0);
    }

    public Integer getInteger(String path, Integer def) {
        Object o = get(path, def);
        return (o instanceof Number) ? ((Number) o).intValue() : def;
    }

    public List<Integer> getIntegerList(String path) {
        List<?>         list    = getList(path);
        List<Integer>   result  = new ArrayList<>();

        for (Object o : list) {
            if (o instanceof Number) {
                result.add(((Number) o).intValue());
            }
        }
        return result;
    }



    public Long getLong(String path) {
        Object o = getDefault(path);
        return getLong(path, (o instanceof Number) ? ((Number) o).longValue() : 0);
    }

    public Long getLong(String path, Long def) {
        Object o = get(path, def);
        return (o instanceof Number) ? ((Number) o).longValue() : def;
    }

    public List<Long> getLongList(String path) {
        List<?>     list    = getList(path);
        List<Long>  result  = new ArrayList<>();

        for (Object o : list) {
            if (o instanceof Number) {
                result.add(((Number) o).longValue());
            }
        }
        return result;
    }



    public Float getFloat(String path) {
        Object o = getDefault(path);
        return getFloat(path, (o instanceof Number) ? ((Number) o).floatValue() : 0);
    }

    public Float getFloat(String path, Float def) {
        Object o = get(path, def);
        return (o instanceof Number) ? ((Number) o).floatValue() : def;
    }

    public List<Float> getFloatList(String path) {
        List<?>     list    = getList(path);
        List<Float> result  = new ArrayList<>();

        for (Object o : list) {
            if (o instanceof Number) {
                result.add(((Number) o).floatValue());
            }
        }
        return result;
    }



    public Double getDouble(String path) {
        Object o = getDefault(path);
        return getDouble(path, (o instanceof Number) ? ((Number) o).doubleValue() : 0);
    }

    public Double getDouble(String path, Double def) {
        Object o = get(path, def);
        return (o instanceof Number) ? ((Number) o).doubleValue() : def;
    }

    public List<Double> getDoubleList(String path) {
        List<?>         list    = getList(path);
        List<Double>    result  = new ArrayList<>();

        for (Object o : list) {
            if (o instanceof Number) {
                result.add(((Number) o).doubleValue());
            }
        }
        return result;
    }



    public Boolean getBoolean(String path) {
        Object o = getDefault(path);
        return getBoolean(path, (o instanceof Boolean) ? (Boolean) o : false);
    }

    public Boolean getBoolean(String path, Boolean def) {
        Object o = get(path, def);
        return (o instanceof Boolean) ? (Boolean) o : def;
    }

    public List<Boolean> getBooleanList(String path) {
        List<?>         list    = getList(path);
        List<Boolean>   result  = new ArrayList<>();

        for (Object o : list) {
            if (o instanceof Boolean) {
                result.add((Boolean) o);
            }
        }
        return result;
    }



    public char getChar(String path) {
        Object o = getDefault(path);
        return getChar(path, (o instanceof Character) ? (Character) o : '\u0000');
    }

    public char getChar(String path, char def) {
        Object o = get(path, def);
        return (o instanceof Character) ? (Character) o : def;
    }

    public List<Character> getCharList(String path) {
        List<?>         list    = getList(path);
        List<Character> result  = new ArrayList<>();

        for ( Object object : list ) {
            if (object instanceof Character) {
                result.add((Character) object);
            }
        }
        return result;
    }



    public String getString(String path) {
        Object o = getDefault(path);
        return getString(path, (o instanceof String) ? (String) o : "");
    }

    public String getString(String path, String def) {
        Object o = get(path, def);
        return (o instanceof String) ? (String) o : def;
    }

    public List<String> getStringList(String path) {
        List<?> list = getList(path);
        List<String> result = new ArrayList<>();

        for (Object o : list) {
            if (o instanceof String) {
                result.add((String) o);
            }
        }
        return result;
    }



    /*-----------------------------------------------------------------------------------------------------------------*/



    public List<?> getList(String path) {
        Object o = getDefault(path);
        return getList(path, (o instanceof List<?>) ? (List<?>) o : Collections.EMPTY_LIST);
    }

    public List<?> getList(String path, List<?> def) {
        Object o = get(path, def);
        return (o instanceof List<?>) ? (List<?>) o : def;
    }
}