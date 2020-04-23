# Table des matières

- [Mise en place du projet](#mise-en-place-du-projet)  
  - [Création du dépôt](#creation-du-depot)
  - [Mise en place de Travis CI](#mise-en-place-de-travis-ci)
  - [Utilisation de Junit et JaCoCo](#utilisation-de-junit-et-jacoco)
  - [Imposer les pull request](#imposer-les-pull-request)
- [Workflow](#workflow)  
  - [Créer une nouvelle branche](#creer-une-nouvelle-branche)
  - [Ouvrir une pull request](#ouvrir-une-pull-request)
  - [Fermer la pull request et créer une release](#fermer-la-pull-request)
  - [Supprimer la branche](#supprimer-la-branche)
- [Déployer le logiciel](#deployer-le-logiciel)  
  - [Déployer sur GitHub Releases](#deployer-sur-github-releases)
  - [Déployer sur GitHub Packages](#deployer-sur-github-packages)


# Mise en place du projet

### Création du dépôt <a name="creation-du-depot"></a>
Pour créer un nouveau projet utilisant **GitHub** et **Maven**, les étapes à suivre sont les suivantes :

1. Créer un dépôt vide sur GitHub
2. Créer un projet localement avec Maven :
```
mvn archetype:generate -DgroupId=domain.mygroup -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart
```
3. Initaliser un dépôt local et créer le premier commit :
```
git init
git add .
git commit -m "Création du projet"
```
4. Lier le dépôt local au dépôt distant :
```
git remote add origin <url-to-github-repo>
```
5. Envoyer le commit sur le dépôt distant :
```
git push -u origin master
```
L'option -u permet de lier la branche *master* locale à la branche distante *origin/master*.

### Mise en place de Travis CI
Pour commencer à utiliser l'intégration continue avec son dépôt GitHub, il faut :

1. Ajouter le dépôt du projet dans les autorisations de Travis CI
2. Ajouter à la racine du projet un fichier ***.travis.yml*** en indiquant simplement le langage de programmation utilisé :
```yml
language: java
```

Dorénavant, chaque push sur le dépôt distant déclenchera un build sur travis.com.

### Utilisation de JUnit et JaCoCo
Pour permettre à Maven d'utiliser les tests créés avec JUnit et de générer automatiquement les rapports de couverture, il faut configurer le fichier **pom.xml** et y ajouter :
```xml
<dependencies>
  <dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.11</version>
    <scope>test</scope>
  </dependency>
</dependencies>

<build>
<plugins>
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>2.3.2</version>
    <configuration>
      <source>7</source>
      <target>7</target>
    </configuration>
  </plugin>

  <plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.2</version>
    <executions>
      <execution>
        <goals>
          <goal>prepare-agent</goal>
        </goals>
      </execution>
      <execution>
        <id>report</id>
        <phase>test</phase>
        <goals>
          <goal>report</goal>
        </goals>
      </execution>
    </executions>
  </plugin>

  <plugin>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.14.1</version>
    <configuration>
      <argLine>${argLine}</argLine>
    </configuration>
  </plugin>
</plugins>
```

### Imposer les pull request
Afin d'éviter les commit direct sur master, GitHub peut être configuré pour forcer l'utilisation de pull request :

1. Ajouter une règle en allant sur son dépôt GitHub dans Settings > Branches et en cliquant sur "Add rule"
2. Mettre la branche *master* comme cible de la règle et sélectionner :  
`- Require status checks to pass before merging`  
`--- Require branches to be up to date before merging`  
`--- Travis CI - Pull Request`
> L'option Travis CI - Pull Request n'apparaît qu'après avoir effectué une première pull request. Il faut donc éditer la règle dès que cette option est disponible.


# Workflow
Le workflow du projet est basé sur celui de GitHub. Pour plus de clarté, nous utiliserons les branches suivantes :
- **master** : branche principale contenant les releases
- **feature/\<feature-name>** : branche de nouvelle fonctionnalité basée sur *master*
- **hotfix/\<hotfix-name>** : branche de correction de bug basée sur *master*

### Créer une nouvelle branche <a name="creer-une-nouvelle-branche"></a>
Pour commencer à implémenter une nouvelle fonctionnalité ou correction de bug, il faut créer une branche basée sur master :
```
git branch <branch-name>
git checkout <branch-name>
git push -u origin <branch-name>
```
> Assurez-vous d'être sur la branche master et d'être à jour par rapport au dépôt distant avant de créer la nouvelle branche.

### Ouvrir une pull request
Une fois la fonctionnalité ou correction terminée, une pull request doit être ouverte sur GitHub.  
Il faut alors mettre à jour la nouvelle branche par rapport à *master* si elle ne l'est pas, puis corriger les éventuels conflits ou erreurs jusqu'à ce que le build de pull request de Travis CI réussisse.

### Fermer la pull request et créer une release <a name="fermer-la-pull-request"></a>
Lorsque la nouvelle branche est jugée prête à la production, il revient à l'administrateur de :
1. **Fusionner la pull request** sur *master*
2. **Changer la version du POM** pour indiquer la version de release (non SNAPSHOT) et commit directement sur master
3. **Créer une release** sur GitHub en la taggant avec "vX.Y.Z"
4. **Ajouter le JAR** à la release
5. **Changer la version du POM** vers une nouvelle version de développment (version SNAPSHOT)

> Le script [release.sh](../scripts/release.sh) permet de simplifier le processus de release en regroupant les étapes 2, 3 et 5. L'étape 4 peut être réalisée automatiquement avec Travis CI (cf. [Déployer sur GitHub releases](#deployer-sur-github-releases)).

### Supprimer la branche
Enfin, lorsque la branche a été fusionnée sur *master* et qu'elle est jugée terminée, elle est fermée sur le dépôt local puis sur le dépôt distant :
```
git branch -d <branch-name>
git push origin --delete <branch-name>
```


# Déployer le logiciel <a name="deployer-le-logiciel"></a>

### Déployer sur GitHub Releases <a name="deployer-sur-github-releases"></a>
Pour ajouter automatiquement le package JAR à une release GitHub en utilisant Travis CI, le fichier **.travis.yml** doit être modifié pour y ajouter la phase de déploiement :
```yml
jobs:
  include:
    - name: test
      if: tag IS blank
      script:
        - mvn test -B

    - name: deploy
      if: tag IS present
      env:
        secure: "...encrypted token..."
      script:
        - mvn package -B
      deploy:
        provider: releases
        api_key: $GITHUB_TOKEN
        file_glob: true
        file: ./target/<my-app>-*.jar
        skip_cleanup: true
        on:
          tags: true
```
Pour obtenir la clé d'accès à GitHub, il faut générer un token sur GitHub et le chiffer avec Travis CI :
1. Aller dans les paramètres de son compte GitHub :  
`Settings > Developer settings > Personal access tokens`
2. Créer un nouveau token et lui donner un nom :  
`automatic releases for <user>/<repo>`
3. Copier le token
4. Ouvrir un terminal et se connecter avec travis :  
```
travis login --pro
```
5. Chiffrer le token :
```
travis encrypt --pro GITHUB_TOKEN="<token>"
```

### Déployer sur GitHub Packages <a name="deployer-sur-github-packages"></a>
Déployer le logiciel sur le dépôt Maven de GitHub Packages nécessite deux choses :
- Ajouter un fichier *settings.xml* dans lequel renseigner la clé d'accès
- Configurer le POM pour y ajouter le dépôt Maven

##### Ajouter un fichier *settings.xml*
La clé d'accès au dépôt doit être indiquée dans un fichier non partagé. Il faut donc ajouter ce fichier **settings.xml** dans le dossier local **~/.m2** (dossier de Maven). Ce fichier devra contenir :  
```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">

  <activeProfiles>
    <activeProfile>github</activeProfile>
  </activeProfiles>

  <profiles>
    <profile>
      <id>github</id>
      <repositories>
        <repository>
          <id>central</id>
          <url>https://repo1.maven.org/maven2</url>
          <releases><enabled>true</enabled></releases>
          <snapshots><enabled>true</enabled></snapshots>
        </repository>
        <repository>
          <id>github-REPO</id>
          <name>GitHub Apache Maven Packages</name>
          <url>https://maven.pkg.github.com/USER/REPO</url>
        </repository>
      </repositories>
    </profile>
  </profiles>

  <servers>
    <server>
      <id>github-REPO</id>
      <username>USER</username>
      <password>TOKEN</password>
    </server>
  </servers>
</settings>
```
en remplaçant *USER*, *REPO* et *TOKEN* par le nom d'utilisateur, le nom du dépôt, et le token d'accès GitHub (vous pouvez réutiliser celui généré pour déployer sur GitHub Releases ou en créer un nouveau).
> Le token devra impérativement avoir les droits de lecture et d'écriture des packages. Il faudra donc penser à cocher **write:packages** et **read:packages** en éditant le token.

##### Configurer le POM
Il faut ensuite ajouter au fichier *pom.xml*, dans la balise *\<project>*, le dépôt Maven vers lequel déployer :
```xml
<distributionManagement>
  <repository>
    <id>github-REPO</id>
    <name>GitHub Apache Maven Packages</name>
    <url>https://maven.pkg.github.com/USER/REPO</url>
  </repository>
</distributionManagement>
```
Le logiciel peut maintenant être déployé sur GitHub Packages en lançant la commande :  
```
mvn deploy
```

##### Déployer automatiquement avec Travis CI
Le déploiement sur GitHub Packages peut être automatisé avec Travis CI.  
Pour cela, Travis CI a besoin du fichier **settings.xml**. Il faut donc copier ce fichier dans un dossier (*.travis/* par exemple) situé à la racine du projet, et remplacer le token par **${GITHUB_TOKEN}** (Travis CI remplacera la variable par le véritable token lors du build sans l'afficher).  

Ensuite, il faut modifier le fichier *.travis.yml* pour changer le script de deploy :  
```yml
jobs:
  include:
    # ...
    - name: deploy  
      if: tag IS present
      before_script:
        - cp ./.travis/settings.xml $HOME/.m2
      script:
        - mvn deploy -B
      # ...
```
