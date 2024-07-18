package model.builder;

import model.family_tree.TreeNode;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Human implements TreeNode<Human> {
    private long id;
    private String name;
    private Gender gender;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private Human mother;
    private Human father;
    private List<Human> children;
    private Human spouse;

    public Human(String name, Gender gender, LocalDate birthDate, LocalDate deathDate, Human father, Human mother) {
        id = -1;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.mother = mother;
        this.father = father;
        children = new ArrayList<>();
    }

    public Human(String name, Gender gender, LocalDate birthDate) {
        this(name, gender, birthDate, null, null, null);
    }

    public Human(String name, Gender gender, LocalDate birthDate, Human father, Human mother) {
        this(name, gender, birthDate, null, father, mother);
    }

    @Override
    public long getId() { return id; }

    @Override
    public void setId(long id) { this.id = id; }

    @Override
    public String getName() { return name; }

    public void setName(String name){ this.name = name; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender){ this.gender = gender; }

    @Override
    public LocalDate getBirthDate() { return birthDate; }
    @Override
    public LocalDate getDeathDate() { return deathDate; }

    public void setDeathDate(LocalDate deathDate) { this.deathDate = deathDate; }
    public Human getMother() { return mother; }
    public void setMother(Human mother) { this.mother = mother; }
    public Human getFather() { return father; }
    public void setFather(Human father) { this.father = father; }

    @Override
    public List<Human> getParents() {
        List<Human> list = new ArrayList<>(2);
        if(father != null) {
            list.add(father);
        }
        if(mother != null) {
            list.add(mother);
        }
        return list;
    }

    @Override
    public List<Human> getChildren() { return children; }

    public void addChild(Human child) {
        if(!children.contains(child)) {
            children.add(child);
        }
    }

    @Override
    public Human getSpouse() {
        return spouse;
    }

    @Override
    public void setSpouse(Human spouse) {
        this.spouse = spouse;
    }

    @Override
    public String toString() { return getInfo(); }
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(id);
        sb.append(", имя: ").append(name);
        sb.append(", пол: ").append(gender);
        sb.append(", дата рождения: ").append(birthDate);
        sb.append(", дата смерти: ").append(deathDate);
        sb.append(", супруг(а): ").append(spouse != null ? spouse.getName() : "нет");
        sb.append(", родители: ").append(getParentsInfo());
        sb.append(", дети: ").append(getChildrenInfo());
        return sb.toString();
    }

    private String getChildrenInfo() {
        StringBuilder sb = new StringBuilder();
        if (!children.isEmpty()) {
            sb.append(children.getFirst().getName());
            for (int i = 1; i < children.size(); i++) {
                sb.append(", ").append(children.get(i).getName());
            }
        } else {
            sb.append("нет");
        }
        return sb.toString();
    }

    private String getParentsInfo() {
        StringBuilder sb = new StringBuilder();
        if (father != null) sb.append("Отец: ").append(father.getName()).append(" ");
        else sb.append("Отец: неизвестен ");
        if (mother != null) sb.append("Мать: ").append(mother.getName());
        else sb.append("Мать: неизвестна");
        return sb.toString();
    }

    public boolean addParent(Human parent) {
        if(parent.getGender().equals(Gender.Male)) {
            setFather(parent);
        } else if (parent.getGender().equals(Gender.Female)) {
            setMother(parent);
        }
        return true;
    }


    public void setParent(Human parent) {
        if(parent.gender.equals(Gender.Female)) {
            setMother(parent);
        } else if (parent.gender.equals(Gender.Male)) {
            setFather(parent);
        }
    }

    public int getAge() {
        if(deathDate == null) {
            return getPeriod(birthDate, LocalDate.now());
        } else {
            return getPeriod(birthDate, deathDate);
        }
    }

    private int getPeriod(LocalDate birthDate, LocalDate deathDate) {
        Period diff = Period.between(birthDate, deathDate);
        return diff.getYears();
    }

    public String getSpouseInfo() {
        String res = "супруг(а): ";
        if(spouse == null) {
            res += "нет";
        } else {
            res += spouse.getName();
        }
        return res;
    }

    public String getMotherInfo() {
        String res = "Мать: ";
        Human mother = getMother();
        if(mother != null) {
            res += mother.getName();
        } else {
            res += "неизвестна";
        }
        return res;
    }

    public String getFatherInfo() {
        String res = "Отец: ";
        Human father = getFather();
        if(father != null) {
            res += father.getName();
        } else {
            res += "неизвестен";
        }
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Human human = (Human) obj;
        return id == human.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
