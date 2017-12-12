# Environment

>ARM Canada inc.

Environment file parser and matcher

## Usage

Used to parse file formatted as below to get environment variable
```
DB_CONNECTION=mysql
DB_HOST=localhost
```

### Basic

#### Load environment

You can load any `List<String>` into the environment. You can either read a file into an `ArrayList` and load it to the environment

```java
public class Main
{
    public static void main(String[] args)
    {
        List<String> values = new ArrayList<>();
        values.add("DB_CONNECTION=mysql");
        values.add("DB_HOST=localhost");
        Environment.load(values);
    }    
}
```

#### Getting a variable

Once you have loaded the variable, you can get them using the `get()` facade method. You can also pass in a second argument as a fallback value
```java

public class Main
{
    public static void main(String[] args)
    {
        Environment.get("DB_CONNECTION"); // would return mysql
        Environment.get("DB_DATABASE", "undefined"); // would return undefined since we did not provided any "DB_DATABASE" key
    }    
}
```

#### Setting a variable
You can override any value at anytime with `set()` method
```java

public class Main
{
    public static void main(String[] args)
    {
        Environment.set("DB_CONNECTION", "sqlserver");
    }    
}
```

### Class property annotation

The package comes with a `@EnvironmentVariable` annotation that allows an automatic assignation with the environment variables. All you have to do is call the `attach()` facade method on any annotated class.

**Note**: Annotated properties must be public  

```java
public class Database
{
    @EnvironmentVariable(key = "DB_CONNECTION")
    public String connection;
}
public class Main
{
    public static void main(String[] args)
    {
        Database db = new Database();
        Environment.attach(db);
        db.connection; // is now equals to "mysql"        
    }    
}
```
