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
  - [Déployer sur Github Releases](#deployer-sur-github-releases)
  - [Mettre à jour la version du POM](#mettre-a-jour-la-version-du-pom)


# Mise en place du projet

### Création du dépôt <a name="creation-du-depot"></a>
Pour créer un nouveau projet utilisant **Github** et **Maven**, les étapes à suivre sont les suivantes :

1. Créer un dépôt vide sur Github
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
Pour commencer à utiliser l'intégration continue avec son dépôt Github, il faut :

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
Afin d'éviter les commit direct sur master, Github peut être configuré pour forcer l'utilisation de pull request :

1. Ajouter une règle en allant sur son dépôt Github dans Settings > Branches et en cliquant sur "Add rule"
2. Mettre la branche *master* comme cible de la règle et sélectionner :  
`- Require status checks to pass before merging`  
`--- Require branches to be up to date before merging`  
`--- Travis CI - Pull Request`
> L'option Travis CI - Pull Request n'apparaît qu'après avoir effectué une première pull request. Il faut donc éditer la règle dès que cette option est disponible.

# Workflow
Le workflow du projet est basé sur celui de Github. Pour plus de clarté, nous utiliserons les branches suivantes :
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
Une fois la fonctionnalité ou correction terminée, une pull request doit être ouverte sur Github.  
Il faut alors mettre à jour la nouvelle branche par rapport à *master* si elle ne l'est pas, puis corriger les éventuels conflits ou erreurs jusqu'à ce que le build de pull request de Travis CI réussisse.

### Fermer la pull request et créer une release <a name="fermer-la-pull-request"></a>
Lorsque la nouvelle branche est jugée prête à la production, il revient à l'administrateur de :
1. **Fusionner la pull request** sur *master*
2. **Changer la version du POM** pour indiquer la version de release (non SNAPSHOT) et commit directement sur master
3. **Créer une release** sur Github en la taggant avec "vX.Y.Z"
4. **Ajouter le JAR** à la release
5. **Changer la version du POM** vers une nouvelle version de développment (version SNAPSHOT)

> Les étapes 4 et 5 peuvent être effectuées automatiquement avec Travis CI (cf. [Déployer sur Github releases](#deployer-sur-github-releases)).

### Supprimer la branche
Enfin, lorsque la branche a été fusionnée sur *master* et qu'elle est jugée terminée, elle est fermée sur le dépôt local puis sur le dépôt distant :
```
git branch -d <branch-name>
git push origin --delete <branch-name>
```

# Déployer le logiciel <a name="deployer-le-logiciel"></a>

### Déployer sur Github Releases <a name="deployer-sur-github-releases"></a>
Pour ajouter automatiquement le package JAR à une release Github en utilisant Travis CI, le fichier **.travis.yml** doit être modifié pour y ajouter la phase de déploiement :
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
Pour obtenir la clé d'accès à Github, il faut générer un token sur Github et le chiffer avec Travis CI :
1. Aller dans les paramètres de son compte Github :  
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

### Mettre à jour la version du POM <a name="mettre-a-jour-la-version-du-pom"></a>
Afin d'éviter d'avoir à modofier manuellement la version du POM vers une nouvelle version de développement après chaque release, il est possible de déléguer cette action à Travis CI.  
Pour cela, il faut ajouter deux scripts bash à son projet (dans un dossier *scripts* par exemple) :  

`increase_minor.sh`
```bash
#!/bin/bash

# Increase MINOR and add "-SNAPSHOT" to POM version

VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

VERSION=${VERSION//./ }         # Split MAJOR, MINOR and PATCH
VERSION=${VERSION//-/ }         # Split apart -SNAPSHOT if any

OldIFS=$IFS
IFS=' '                         # Set space as delimiter
read -ra nums <<< "$VERSION"    # nums is read into an array as tokens separated by IFS
IFS=$OldIFS

lastIndex=$( expr ${#nums[@]} - 1 )

# Don't update version if already a SNAPSHOT
if [ ${nums[$lastIndex]} != "SNAPSHOT" ]
then
  newMINOR=$( expr ${nums[1]} + 1)
  newVERSION="${nums[0]}.$newMINOR-SNAPSHOT"

  mvn versions:set -DnewVersion=$newVERSION
fi
```
`push_pom.sh`
```bash
#!/bin/bash

# Push pom.xml file on github

# Configure git
git config --global user.email "travis@travis-ci.com"
git config --global user.name "Travis CI"
git checkout -b master

# Commit pom.xml
git add pom.xml
git commit -m "Prepare la prochaine release (version SNAPSHOT)"

# Push pom.xml
git remote add origin-travis https://${GITHUB_TOKEN}@github.com/<user>/<repo>.git
git push --quiet --set-upstream origin-travis master
```
> Seul le script *push_pom.xml* est à modifier pour y indiquer son dépôt distant (*\<user>* et *\<repo>*).

Enfin, il faut ajouter au job *deploy* de son fichier *.travis.yml* la phase *after_deploy* :
```yml
jobs:
  include:
    # ...
    - name: deploy
      # ...
      after_deploy:
        - bash scripts/increase_minor.sh
        - bash scripts/push_pom.sh
```

Une fois ces modifications faites, Travis CI modifie la version du POM après chaque release pour mettre une nouvelle version de développement (incrémente le MINOR et ajoute le suffixe "-SNAPSHOT") et l'envoie directement sur *master*.  
> Si la prochaine version en développement est une nouvelle version MAJOR, la modification du POM doit être faite manuellement par l'administrateur.
