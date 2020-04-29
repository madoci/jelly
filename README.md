# Jelly

[![GitHub release (latest by date)](https://img.shields.io/github/v/release/madoci/jelly.svg)](https://github.com/madoci/jelly/releases)
[![Build Status](https://travis-ci.org/madoci/jelly.svg?branch=master)](https://travis-ci.org/madoci/jelly)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![codecov](https://codecov.io/gh/madoci/jelly/branch/master/graph/badge.svg)](https://codecov.io/gh/madoci/jelly)
[![Maintainability](https://api.codeclimate.com/v1/badges/ac1ddf48d5c45e24e622/maintainability)](https://codeclimate.com/github/madoci/jelly/maintainability)
[![GitHub Release Date](https://img.shields.io/github/release-date/madoci/jelly.svg)](https://github.com/madoci/jelly/releases)

## Table des matières


## Fonctionnalités
Jelly est une bibliothèque d'analyse de données en Java. Elle propose plusieurs classes et méthodes permettant de manipuler et analyser facilement des ensembles de données.  

Le Dataframe est la structure de base de la bibliothèque Jelly. Il s'agit d'une classe permettant de stocker des tableaux à deux dimensions. Chaque colonne est identifiée par un label et contient des données d'un seul type. Deux colonnes d'un même dataframe peuvent en revanche contenir des données de types différents. Les lignes d'un dataframe sont indexées à partir de 0.

#### Construction d'un dataframe
Il est possible de construire un dataframe de trois manières différentes :
- à partir des types de données de chaque colonne
- à partir de tableaux d'objets
- à partir d'un fichier CSV

###### A partir des types de données
Pour constuire un dataframe à partir des types de données de chaque colonne, il faut donner au constructeur un tableau de labels suivi d'un tableau de classes, données dans le même ordre que les labels :
```java
public Dataframe(String[] labels, Class<?>[] types)
```
Il est ensuite possible d'ajouter des lignes à la fin du dataframe avec la méthode addRow :
```java
public void addRow(Object row[])
```
L'ordre des objets dans le tableau passé en paramètre doit être le même que celui des colonnes défini lors de la construction du dataframe. Si un des objets donnés ne correspond pas au type de la colonne dans laquelle il est ajouté, une IllegalArgumentException est levée.  

*Exemple :*
```java
String[] labels = { "A", "B" };
Class<?>[] types = { String.class, Integer.class };
Dataframe dataframe = new Dataframe(labels, types);

String A = "Exemple";
Integer b = 12;
Object[] row = { a, b };
dataframe.addRow(row);
```

###### A partir de tableaux d'objets
Pour construire un dataframe à partir de tableaux d'objets, il faut donner au constructeur un tableau de labels suivi d'un de tableau d'objets par colonne :
```java
public Dataframe(String[] labels, Object[] ...data)
```
Cette méthode détermine le type de chaque colonne automatiquement en fonction des objets donnés. Si un des tableaux contient des objets de type différents ou uniquement des références nulles, une IllegalArgumentException sera levée.  

*Exemple :*
```java
String[] labels = { "A", "B" };
String[] columnA = { "1", "2" };
Integer[] columnB = { 12, 0 }
Dataframe dataframe = new Dataframe(labels, columnA, columnB);
```
> Le nombre de lignes finales du dataframe est déterminé par la taille de la plus grande colonne passée en paramètre. Les autres colonnes seront remplies par des références nulles pour avoir le même nombre de lignes.

###### A partir d'un fichier CSV
Pour construire un dataframe à partir d'un fichier CSV, il faut simplement donner au constructeur le chemin du fichier :
```java
public Dataframe(String pathname)
```
La première ligne du fichier CSV correspond aux labels des colonnes, et chaque ligne suivante correspond à une ligne du dataframe. Les types des colonnes sont interprétés automatiquement. Seuls les chaînes de caractères (*String*), les entiers (*int*) et les réels (*double*) sont reconnus.  

Le fichier CSV devra respecter les conventions du format CSV (cf. https://en.wikipedia.org/wiki/Comma-separated_values#Basic_rules). Si le format n'est pas respecté, une InvalidCSVFormatException sera levée.
> Il est actuellement impossible de mettre un retour à la ligne dans un champ, même s'il est entouré de guillemets comme spécifié dans les règles du format CSV.

#### Accès aux données d'un dataframe
L'accès aux données d'un dataframe se fait par l'intermédaire de plusieurs méthodes, notamment :
- **get(i, j)** : renvoie l'élément à la ligne i colonne j
- **get(i, label)** : renvoie l'élément à la ligne i et la colonne identifiée par label

> D'autres méthodes existent et permettent d'accéder au label et type d'une colonne, à toute une ligne, à toute une colonne, ou bien encore au nombre de lignes et de colonnes dans un dataframe. Pour plus d'informations à propos de ces méthodes, consultez la documentation.

#### Affichage d'un dataframe
Il est posssible d'afficher rapidement un dataframe pour visualiser ses données, soit en utilisant l'affichage par défaut, soit en le personnalisant.

###### Visualiser les données
Pour visualiser les données d'un dataframe, plusieurs méthodes sont disponibles :
- **view()** : affiche la totailté du dataframe
- **head() / head(num)** : affiche les premières lignes du dataframe (num permet de définir le nombre de lignes affichées)
- **tail() / tail(num)** : affiche les premières lignes du dataframe

> Les méthodes *head()* et *tail()* affiche un nombre de lignes prédéfini (5 par défaut). Les méthodes *head(num)* et *tail(num)* permettent de définir précisément le nombre de lignes à afficher.

Ces méthodes renvoient toutes une chaîne de caractères (*String*) correpondant à la représentation du dataframe.
> La méthode *toString()* de Dataframe renvoie le même résultat que *view()*.

###### Personnaliser l'affichage
L'affichage d'un dataframe se fait par l'intermédaire d'un objet implémentant l'interface DataframeViewer. Il est possible de changer le viewer utilisé par un dataframe avec la méthode :
```java
public void setViewer(DataframeViewer viewer)
```
La classe TabularDataframeViewer permet ainsi de personnaliser l'affichage simplement, en changeant le séparateur entre les colonnes ou le nombre de lignes affichées par défaut lors de l'appel à *head()* ou *tail()*.

*Exemple :*
```java
TabularDataframeViewer viewer = new TabularDataframeViewer();
viewer.setSeparator(" | ");
dataframe.setViewer(viewer);
```

#### Sélection sur un dataframe
Afin de pouvoir extraire des sous-ensembles d'un dataframe, il est possible d'effectuer des sélections de plusieurs types différents :
- sélection de lignes ou colonnes spécifiées par leurs indices ou labels
- sélection de lignes remplissant une condition

Ces sélections renvoient un nouveau dataframe correspondant au sous-ensemble souhaité. Elles sont effectuées par un objet DataframeSelection accessible avec la méthode *select()* du dataframe (*select()* renvoie un outil de sélection associé au dataframe appelant).

> Les dataframes créés par sélection utilisent le même viewer que le dataframe original. Si un affichage personnalisé a été défini pour un dataframe, il n'est donc pas nécesssaire de le redéfinir pour ses sous-ensembles.

###### Sélectionner des lignes et colonnes spécifiques
Cinq méthodes existent permettant de sélectionner certaines lignes et certaines colonnes spécifiques dans un dataframe :
- **row(rows)** : renvoie un dataframe avec uniquement les lignes spécifiées et toutes les colonnes
- **column(columns) / column(labels)** : renvoie un dataframe avec uniquement les colonnes spécifiées (par indices ou par labels) et toutes les lignes
- **cross(rows, columns) / cross(rows, labels)** : renvoie un dataframe avec uniquement les lignes et les colonnes spécifiées (par indices ou par labels)

*Exemple :*
```java
int[] rows = { 0, 2, 3, 5 };
String labels = { "B", "A" };

Dataframe a = dataframe.select().row(rows);
Dataframe b = dataframe.select().column(labels);
Dataframe c = dataframe.select().cross(rows, labels);
```
> L'ordre des lignes et des colonnes du dataframe résultant est le même que celui des tableaux donnés en paramètres. Il est donc possible de modifier l'ordre des lignes et des colonnes d'un dataframe avec une sélection.

###### Sélectionner des lignes selon une condition
La sélection peut également être faite en imposant une condition sur les champs d'une colonne. Les méthodes permettant d'effectuer de telles sélections sont les suivantes :
- **equal(label, value)** : renvoie un dataframe avec uniquement les lignes dont le champ de la colonne *label* est égal à *value*
- **notEqual(label, value)** : renvoie un dataframe avec uniquement les lignes dont le champ de la colonne *label* est différent de *value*
- **lessThan(label, value)** : renvoie un dataframe avec uniquement les lignes dont le champ de la colonne *label* est strictement inférieur à *value*
- **lessEqual(label, value)** : renvoie un dataframe avec uniquement les lignes dont le champ de la colonne *label* est inférieur ou égal à *value*
- **greaterThan(label, value)** : renvoie un dataframe avec uniquement les lignes dont le champ de la colonne *label* est strictement supérieur à *value*
- **greaterEqual(label, value)** : renvoie un dataframe avec uniquement les lignes dont le champ de la colonne *label* est supérieur ou égal à *value*

La valeur passée en paramètre doit être comparable au type de la colonne spécifiée. Si ce n'est pas le cas, une IllegalArgumentException sera levée.

*Exemple :*
```java
Dataframe a = dataframe.select().equal("Prix", 2.50);
Dataframe b = dataframe.select().lessThan("Nom", "F");
```

> Les conditons sur les références nulles sont toujours fausses, même dans le cas de *notEqual*. Lors d'une sélection par condition, toutes les lignes dont le champ de la colonne spécifiée est une référence nulle seront donc ignorées.

#### Statistiques sur un dataframe
Des calculs de statistiques de base peuvent être exécutés sur les colonnes d'un dataframe.
Les méthodes de calcul sont fournises par un objet DataframeStatistics associé à un dataframe, accessible via sa méthode *stats()*.

###### Effectuer des calculs statistiques
Les méthodes fournises permettent de calculer :
- **argmin / argmax** : indice de la ligne contenant la valeur minimale / maximale de la colonne spécifiée
- **min / max** : valeur minimale / maximale de la colonne spécifiée
- **sum** : somme sur toutes les lignes des champs de la colonne spécifiée
- **mean** : moyenne sur toutes les lignes des champs de la colonne spécifiée
- **median** : médiane de la colonne spécifiée

*Exemple :*
```java
int argmin = dataframe.stats().argmin("Prix");
double sum = dataframe.stats().sum("Prix");
```

> Les références nulles sont toujours ignorées dans les calculs statistiques. Elles ne sont donc pas comptabilisées non plus dans la moyenne.

###### Configurer les calculs statistiques
Le calcul des statistiques d'un dataframe passe par l'utilisation des méthodes statiques de la classe ArrayStatistics permettant d'effectuer les calculs sur des tableaux d'objets. ArrayStatistics utilise à son tour des objets implémentant l'interface Operator pour effectuer ses calculs sur des objets de types différents. Chaque opérateur est associé au type d'objet sur lequel il opère. Il est possible d'ajouter ou modifier des opérateurs, autorisant notamment les calculs statistiques sur des objets de ses propres classes.

> Par défaut, seuls deux opérateurs sont définis : IntegerStrictOperator pour les entiers, et DoubleOperator pour les doubles et toute autre classes implémentant Number. L'opérateur IntegerOperator existe également et peut remplacer IntegerStrictOperator si l'on souhaite obtenir des doubles lors des calculs de moyenne et de médiane.

*Exemple :*
```java
ArrayStatistics.setOperator(Integer.class, new IntegerOperator());
```

La démo **mathset** fournit un exemple d'opérateur personnalisé pour les ensembles d'entiers (cf. [IntegerSetOperator](#demo/mathset/src/main/java/fr/uga/fran/mathset/IntegerSetOperator.java)).


## Outils utilisés




## Image Docker


## Feedback
