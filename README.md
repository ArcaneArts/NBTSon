# NBTSon

Gain the advantage of a similar storage footprint with the advantage of your object being the codec, with the power of Gson type adapters!

Essentially Objects are converted to Json first, using gson, then are converted to NBT after. This ensures types like Enums & lists are properly serialized, this also means gson type adapters work fine.

Deserialization works just the same. Json is created from the NBT then it's processed by Gson back into your object!

![](https://img.shields.io/github/v/release/ArcaneArts/NBTSon?color=%236f24f0&display_name=tag&label=NBTSon&sort=semver&style=for-the-badge)

```groovy
maven { url "https://arcanearts.jfrog.io/artifactory/archives" }
```

```groovy
implementation 'art.arcane:NBTSon:<VERSION>'
```

## Usage

Serialization
```java
SomeObject object = new SomeObject();
CompoundTag tag = NBTSon.toNBT(object);
String snbt = NBTSon.toSNBT(object);
```

Deserialization
```java
CompoundTag tag = ...
SomeObject object = NBTSon.fromNBT(SomeObject.class, tag);
```

Custom Gson with Adapters
```java
Gson gson = new GsonBuilder()
    // We need to support booleans via 0 / 1 numbers because nbt
    .registerTypeAdapterFactory(new BooleanTypeAdapterFactory())
    .create();

NBTSon.toNBT(object, gson);
NBTSon.toSNBT(object, gson);

NBTSon.fromNBT(object, clazz, gson);
NBTSon.fromSNBT(object, clazz, gson);
```

## Performance
You can run this test by running :test in this project. This test was 10,000x runs each.

```
Gson: 132ms
NBT : 207ms
SNBT: 299ms
```

## Footprint

While NBT is not generally smaller when keys are large, it can be comparable to json

```
Json            : 499 Bytes
Compressed Json : 218 Bytes
Uncompressed NBT: 399 Bytes
Compressed NBT  : 220 Bytes
```
